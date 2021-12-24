package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;

@Service
@RequiredArgsConstructor
public class IzvestajOImunizacijiService {
    private final IDao<IzvestajOImunizaciji> izvestajOImunizacijiIDao;
    private final Validator validator;
    private final IModelMapper<IzvestajOImunizaciji> mapper;

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

        return izvestajOImunizacijiIDao.save(izvestajOImunizaciji);
    }

}
