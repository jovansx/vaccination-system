package akatsuki.officialsystem.service;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.exceptions.ConflictRuntimeException;
import akatsuki.officialsystem.exceptions.NotFoundRuntimeException;
import akatsuki.officialsystem.model.documents.Interesovanje;
import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.izvestaji.IzvestajOImunizacijiPeriodDTO;
import akatsuki.officialsystem.model.izvestaji.IzvestajiOImunizacijiDTO;
import akatsuki.officialsystem.model.types.TCBrojInteresovanja;
import akatsuki.officialsystem.model.types.TCIzdatoZelenih;
import akatsuki.officialsystem.model.types.TCPrimljenoZelenih;
import akatsuki.officialsystem.model.types.TNazivVakcine;
import akatsuki.officialsystem.model.vaccine.RaspodelaPoDozamaDTO;
import akatsuki.officialsystem.model.vaccine.RaspodelaPoProizvodjacimaDTO;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.model.vaccine.VaccineType;
import akatsuki.officialsystem.utils.CalendarPeriod;
import akatsuki.officialsystem.utils.MetadataExtractor;
import akatsuki.officialsystem.utils.Validator;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class IzvestajOImunizacijiService {
    private final IDao<IzvestajOImunizaciji> izvestajOImunizacijiIDao;
    private final Validator validator;
    private final IModelMapper<IzvestajOImunizaciji> mapper;
    private final IModelMapper<RaspodelaPoProizvodjacimaDTO> raspodelaPoProizvodjacimaDTOmapper;
    private final IModelMapper<RaspodelaPoDozamaDTO> raspodelaPoDozamaDTOmapper;
    private final IModelMapper<IzvestajiOImunizacijiDTO> izvestajiDTOmapper;
    private final MetadataExtractor extractor;
    private final RestTemplate restTemplate;

    public String getIzvestajOImunizaciji(String periodOdDo) throws RuntimeException {
        if (!validator.isDatePeriodOk(periodOdDo))
            throw new BadRequestRuntimeException("Poslati period " + periodOdDo + " nije validan.");

        IzvestajOImunizaciji izvestajOImunizaciji = izvestajOImunizacijiIDao.get(periodOdDo).
                orElseThrow(() -> new NotFoundRuntimeException("Nije podnet izvestaj u periodu " + periodOdDo + "."));
        return mapper.convertToXml(izvestajOImunizaciji);
    }

    public String getIzvestajOImunizacijiByPeriod(String periodOd, String periodDo) {
        XMLGregorianCalendar datumIzdavanjaIzvestaja = CalendarPeriod.getXmlGregorianCalendarByDateNow();
        String about = "http://www.akatsuki.org/izvestaji/" + periodOd + "_" + periodDo;

        IzvestajOImunizaciji.Period period = getPeriod(periodOd, periodDo);
        IzvestajOImunizaciji.Dokumenti dokumenti = getDokumenti(periodOd, periodDo);
        List<IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama.Doza> doze = getDoze(periodOd, periodDo);

        int ukupnoIzdato = doze.get(0).getKolicina() + doze.get(1).getKolicina();
        IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama raspodelaPoDozama = new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama(doze);
        IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima raspodelaPoProizvodjacima = new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima(getProizvodjaciVakcina(periodOd, periodDo));
        IzvestajOImunizaciji.DozeVakcina dozeVakcina = new IzvestajOImunizaciji.DozeVakcina(raspodelaPoDozama, raspodelaPoProizvodjacima, ukupnoIzdato);

        IzvestajOImunizaciji izvestajOImunizaciji = new IzvestajOImunizaciji(period, dokumenti, dozeVakcina, datumIzdavanjaIzvestaja, about);

        return mapper.convertToXml(izvestajOImunizaciji);
    }

    private IzvestajOImunizaciji.Period getPeriod(String periodOd, String periodDo) {
        XMLGregorianCalendar periodOdXmlDate = CalendarPeriod.getXmlGregorianCalendarByString(periodOd);
        XMLGregorianCalendar periodDoXmlDate = CalendarPeriod.getXmlGregorianCalendarByString(periodDo);
        return new IzvestajOImunizaciji.Period(periodOdXmlDate, periodDoXmlDate);
    }

    private List<IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac> getProizvodjaciVakcina(String periodOd, String periodDo) {
        List<IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac> proizvodjaciVakcina = new ArrayList<>();

        ResponseEntity<String> responseString = restTemplate.getForEntity("http://localhost:8081/api/potvrde/proizvodjaci/" + periodOd + "/" + periodDo, String.class);
        String xmlRaspodelaPoProizvodjacima = responseString.getBody();
        RaspodelaPoProizvodjacimaDTO raspodelaPoProizvodjacimaDTO = raspodelaPoProizvodjacimaDTOmapper.convertToObject(xmlRaspodelaPoProizvodjacima);

        proizvodjaciVakcina.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac(
                TNazivVakcine.PFIZER_BIO_N_TECH, raspodelaPoProizvodjacimaDTO.getPfizerBioNTech()
        ));
        proizvodjaciVakcina.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac(
                TNazivVakcine.SPUTNIK_V_GAMALEYA_ISTRAZIVACKI_CENTAR, raspodelaPoProizvodjacimaDTO.getSputnikV()
        ));
        proizvodjaciVakcina.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac(
                TNazivVakcine.SINOPHARM, raspodelaPoProizvodjacimaDTO.getSinopharm()
        ));
        proizvodjaciVakcina.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac(
                TNazivVakcine.ASTRA_ZENECA, raspodelaPoProizvodjacimaDTO.getAstraZeneca()
        ));
        proizvodjaciVakcina.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima.Proizvodjac(
                TNazivVakcine.MODERNA, raspodelaPoProizvodjacimaDTO.getModerna()
        ));

        return proizvodjaciVakcina;
    }

    private List<IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama.Doza> getDoze(String periodOd, String periodDo) {
        List<IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama.Doza> doze = new ArrayList<>();
        ResponseEntity<String> responseString = restTemplate.getForEntity("http://localhost:8081/api/potvrde/doze/" + periodOd + "/" + periodDo, String.class);
        String xmlRaspodelaPoDozama = responseString.getBody();
        RaspodelaPoDozamaDTO raspodelaPoDozamaDTO = raspodelaPoDozamaDTOmapper.convertToObject(xmlRaspodelaPoDozama);

        doze.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama.Doza(1, raspodelaPoDozamaDTO.getDoza1()));
        doze.add(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama.Doza(2, raspodelaPoDozamaDTO.getDoza2()));
        return doze;
    }

    private IzvestajOImunizaciji.Dokumenti getDokumenti(String periodOd, String periodDo) {
        int brojInteresovanja = getBrojInteresovanja(periodOd, periodDo);
        TCBrojInteresovanja tcBrojInteresovanja = new TCBrojInteresovanja(brojInteresovanja, "pred:broj_interesovanja", "xs:int");

        int brojZahteva = getBrojZahteva(periodOd, periodDo);
        TCPrimljenoZelenih tcPrimljenoZelenih = new TCPrimljenoZelenih(brojZahteva, "pred:primljeno_zelenih", "xs:int");

        int brojSertifikata = getBrojSertifikata(periodOd, periodDo);
        TCIzdatoZelenih tcIzdatoZelenih = new TCIzdatoZelenih(brojSertifikata, "pred:izdato_zelenih", "xs:int");

        IzvestajOImunizaciji.Dokumenti.ZeleniSertifikat zeleniSertifikat = new IzvestajOImunizaciji.Dokumenti.ZeleniSertifikat(tcPrimljenoZelenih, tcIzdatoZelenih);

        return new IzvestajOImunizaciji.Dokumenti(tcBrojInteresovanja, zeleniSertifikat);
    }

    private int getBrojSertifikata(String periodOd, String periodDo) {
        ResponseEntity<Integer> response = restTemplate.getForEntity("http://localhost:8081/api/digitalni-sertifikati/" + periodOd + "/" + periodDo, Integer.class);
        return response.getBody();
    }

    private int getBrojZahteva(String periodOd, String periodDo) {
        ResponseEntity<Integer> response = restTemplate.getForEntity("http://localhost:8081/api/zahtevi/" + periodOd + "/" + periodDo, Integer.class);
        return response.getBody();
    }

    private int getBrojInteresovanja(String periodOd, String periodDo) {
        ResponseEntity<Integer> response = restTemplate.getForEntity("http://localhost:8081/api/interesovanja/" + periodOd + "/" + periodDo, Integer.class);
        return response.getBody();
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
