package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.PdfTransformer;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteresovanjeService {
    private final IDao<Interesovanje> interesovanjeDAO;
    private final Validator validator;
    private final IModelMapper<Interesovanje> mapper;
    private final MetadataExtractor extractor;
    private final DaoUtils utils;
    private final EmailService emailService;
    private final PdfTransformer pdfTransformer;

    private final IDao<Appointment> appointmentIDao;
    private final IModelMapper<Appointment> mapper2;


    public String getInteresovanje(String idBroj) throws RuntimeException {
        if (!validator.isIdValid(idBroj))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");

        Interesovanje interesovanje = interesovanjeDAO.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Osoba s id-om " + idBroj + " nije podnela interesovanje za vakcinacijom."));
        return mapper.convertToXml(interesovanje);
    }

    public String createInteresovanje(String interesovanjeXml) throws RuntimeException {
        Interesovanje interesovanje = mapper.convertToObject(interesovanjeXml);
        if (interesovanje == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (interesovanjeDAO.get(interesovanje.getPodnosilac().getIdBroj().getValue()).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + interesovanje.getPodnosilac().getIdBroj().getValue() + " je vec podnela interesovanje za vakcinacijom.");

        if (!extractor.extractAndSaveToRdf(interesovanjeXml, "/interesovanja"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        Appointment appointment = createAppointment(interesovanje);
        emailService.notifyPatientAboutReservedAppointment(interesovanje, appointment);

        return interesovanjeDAO.save(interesovanje);
    }

    public void setReference(String objectId, String referencedObjectId) {
        Interesovanje interesovanje = interesovanjeDAO.get(objectId).get();
        interesovanje.setRel("pred:parentTo");
        interesovanje.setHref("http://www.akatsuki.org/saglasnosti/" + referencedObjectId);

        interesovanjeDAO.save(interesovanje);
    }

    private Appointment createAppointment(Interesovanje interesovanje) {
        try {
            List<String> retVal = utils.execute("(//termin/text())[last()]", "/db/vaccination-system/termini");
            String[] parts = retVal.get(0).split(":[0-9]{2}.[0-9]{3}\\+[0-9]{2}.[0-9]{2}");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = format2.parse(parts[0]);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
            Appointment appointment = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), interesovanje.getPodnosilac().getIdBroj().getValue());
            appointmentIDao.save(appointment);
            return appointment;
        } catch (Exception ignored) {}
        return null;
    }

    public void generatePdf(String idBroj) {
        try {
            pdfTransformer.generatePDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
