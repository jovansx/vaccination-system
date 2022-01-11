package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.*;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteresovanjeService {
    private final IDao<Interesovanje> interesovanjeDAO;
    private final Validator validator;
    private final IModelMapper<Interesovanje> mapper;
    private final MetadataExtractor extractor;
    private final EmailService emailService;
    private final PdfTransformer pdfTransformer;
    private final HtmlTransformer htmlTransformer;

    private final AppointmentService appointmentService;


    public String getInteresovanje(String idBroj) throws RuntimeException {
        if (!validator.isIdValid(idBroj))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");

        Interesovanje interesovanje = interesovanjeDAO.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Osoba s id-om " + idBroj + " nije podnela interesovanje za vakcinacijom."));
        return mapper.convertToXml(interesovanje);
    }

    public int getResourcesCountInPeriod(String periodOd, String periodDo) {
        List<Interesovanje> allInteresovanja = (List<Interesovanje>) interesovanjeDAO.getAll();
        CalendarPeriod.calendarSetTimeByPeriod(periodOd, periodDo);
        int count = 0;
        for (Interesovanje i : allInteresovanja) {
            Calendar datumPodnosenjaInteresovanja = i.getDatumPodnosenja().toGregorianCalendar();
            if (datumPodnosenjaInteresovanja.compareTo(CalendarPeriod.periodOdCal) == 1 && datumPodnosenjaInteresovanja.compareTo(CalendarPeriod.periodDoCal) == -1) {
                count++;
            }

        }
        return count;
    }

    public void createInteresovanje(String interesovanjeXml) throws RuntimeException {
        Interesovanje interesovanje = mapper.convertToObject(interesovanjeXml);
        if (interesovanje == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (interesovanjeDAO.get(interesovanje.getPodnosilac().getIdBroj().getValue()).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + interesovanje.getPodnosilac().getIdBroj().getValue() + " je vec podnela interesovanje za vakcinacijom.");

        if (!extractor.extractAndSaveToRdf(interesovanjeXml, "/interesovanja"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        Appointment appointment = appointmentService.createAppointment(interesovanje.getPodnosilac().getIdBroj().getValue(), 1);
        emailService.notifyPatientAboutReservedAppointment(interesovanje, appointment);

        interesovanjeDAO.save(interesovanje);
    }

    public void setReference(String objectId, String referencedObjectId) {
        Interesovanje interesovanje = interesovanjeDAO.get(objectId).get();
        interesovanje.setRel("pred:parentTo");
        interesovanje.setHref("http://www.akatsuki.org/saglasnosti/" + referencedObjectId);

        interesovanjeDAO.save(interesovanje);
    }

    public ByteArrayInputStream generatePdf(String idBroj) {
        return pdfTransformer.generatePDF(getInteresovanje(idBroj), Interesovanje.class);
    }

    public ByteArrayInputStream generateXhtml(String idBroj) {
        return htmlTransformer.generateHTML(getInteresovanje(idBroj), Interesovanje.class);
    }

    public MetadataDTO getMetadataJSON(String idBroj) {
        return new MetadataDTO("<http://www.akatsuki.org/interesovanja/" + idBroj + ">", extractor.readFromRdfWhereObjectIs("/interesovanja", idBroj));
    }

    public String getMetadataRDF(String idBroj) {
        return extractor.getRdfMetadata("/interesovanja", idBroj);
    }
}
