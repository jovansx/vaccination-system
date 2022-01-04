package akatsuki.officialsystem.service;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.exceptions.ConflictRuntimeException;
import akatsuki.officialsystem.exceptions.NotFoundRuntimeException;
import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.izvestaji.IzvestajOImunizacijiPeriodDTO;
import akatsuki.officialsystem.model.izvestaji.IzvestajiOImunizacijiDTO;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.utils.MetadataExtractor;
import akatsuki.officialsystem.utils.Validator;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IzvestajOImunizacijiService {
    private final IDao<IzvestajOImunizaciji> izvestajOImunizacijiIDao;
    private final Validator validator;
    private final IModelMapper<IzvestajOImunizaciji> mapper;
    private final IModelMapper<IzvestajiOImunizacijiDTO> izvestajiDTOmapper;
    private final MetadataExtractor extractor;

    public String getIzvestajOImunizaciji(String periodOdDo) throws RuntimeException {
        if (!validator.isDatePeriodOk(periodOdDo))
            throw new BadRequestRuntimeException("Poslati period " + periodOdDo + " nije validan.");

        IzvestajOImunizaciji izvestajOImunizaciji = izvestajOImunizacijiIDao.get(periodOdDo).
                orElseThrow(() -> new NotFoundRuntimeException("Nije podnet izvestaj u periodu " + periodOdDo + "."));
        return mapper.convertToXml(izvestajOImunizaciji);
    }

    public String createIzvestajOImunizaciji(String izvestajOImunizacijiXml) throws RuntimeException {
        IzvestajOImunizaciji izvestajOImunizaciji = mapper.convertToObject(izvestajOImunizacijiXml);
        if (izvestajOImunizaciji == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        XMLGregorianCalendar firstDate = izvestajOImunizaciji.getPeriod().getOd();
        XMLGregorianCalendar secondDate = izvestajOImunizaciji.getPeriod().get_do();
        if (!validator.isDateBeforeAnotherDate(firstDate, secondDate))
            throw new ConflictRuntimeException("Datum OD mora da bude pre datuma DO.");

        String periodOdDo = izvestajOImunizaciji.getPeriod().getOd() + "_" + izvestajOImunizaciji.getPeriod().get_do();
        if (izvestajOImunizacijiIDao.get(periodOdDo).isPresent())
            throw new ConflictRuntimeException("U periodu od " + firstDate + " do " + secondDate + " je vec podnet izvestaj.");

        if (!extractor.extractAndSaveToRdf(izvestajOImunizacijiXml, "/izvestaji"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");
        return izvestajOImunizacijiIDao.save(izvestajOImunizaciji);
    }

    public String getAll() {
        List<IzvestajOImunizaciji> allIzvestaji = (List<IzvestajOImunizaciji>) izvestajOImunizacijiIDao.getAll();
        List<IzvestajOImunizacijiPeriodDTO> izvestajPeriod = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        for(IzvestajOImunizaciji izvestaj : allIzvestaji) {
            Calendar calendarOd = izvestaj.getPeriod().getOd().toGregorianCalendar();
            Calendar calendarDo = izvestaj.getPeriod().get_do().toGregorianCalendar();
            formatter.setTimeZone(calendarOd.getTimeZone());
            String periodOd = formatter.format(calendarOd.getTime());
            formatter.setTimeZone(calendarDo.getTimeZone());
            String periodDo = formatter.format(calendarDo.getTime());
            String period = periodOd + " - " + periodDo;
            String id = periodOd + "_" + periodDo;

            izvestajPeriod.add(new IzvestajOImunizacijiPeriodDTO(id, period));
        }
        IzvestajiOImunizacijiDTO izvestajiDTO = new IzvestajiOImunizacijiDTO(izvestajPeriod);
        return izvestajiDTOmapper.convertToXml(izvestajiDTO);
    }
}
