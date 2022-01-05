package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DigitalniSertifikatDAO;
import akatsuki.immunizationsystem.dao.ISaglasnostZaImunizacijuDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.PdfTransformer;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class SaglasnostZaImunizacijuService {
    private ISaglasnostZaImunizacijuDAO saglasnostZaImunizacijuIDao;
    private DigitalniSertifikatDAO digitalniSertifikatDAO;
    private Validator validator;
    private IModelMapper<SaglasnostZaImunizaciju> mapper;
    private IModelMapper<Interesovanje> interesovanjeIModelMapper;
    private MetadataExtractor extractor;
    private InteresovanjeService interesovanjeService;
    private PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService;
    private PdfTransformer pdfTransformer;
    private AppointmentService appointmentService;

    private EmailService emailService;

    @Autowired
    public void setValidator(Validator validator, ISaglasnostZaImunizacijuDAO saglasnostZaImunizacijuIDao, DigitalniSertifikatDAO digitalniSertifikatDAO,
                             IModelMapper<SaglasnostZaImunizaciju> mapper, IModelMapper<Interesovanje> interesovanjeIModelMapper, MetadataExtractor extractor,
                             InteresovanjeService interesovanjeService,
                             AppointmentService appointmentService, EmailService emailService,
                             @Lazy PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService, PdfTransformer pdfTransformer) {
        this.saglasnostZaImunizacijuIDao = saglasnostZaImunizacijuIDao;
        this.digitalniSertifikatDAO = digitalniSertifikatDAO;
        this.validator = validator;
        this.mapper = mapper;
        this.extractor = extractor;
        this.interesovanjeService = interesovanjeService;
        this.potvrdaOIzvrsenojVakcinacijiService = potvrdaOIzvrsenojVakcinacijiService;
        this.pdfTransformer = pdfTransformer;
        this.appointmentService = appointmentService;
        this.emailService = emailService;
        this.interesovanjeIModelMapper = interesovanjeIModelMapper;
    }

    public String getSaglasnostByPatientId(String patientId) {
        String saglasnostXml = "";
        try {
            saglasnostXml = getSaglasnostZaImunizaciju(patientId + "_2");
        } catch (Exception ignored) {
        }
        if (saglasnostXml.equals("")) {
            try {
                saglasnostXml = getSaglasnostZaImunizaciju(patientId + "_1");
            } catch (Exception ignored) {
            }
        }
        return saglasnostXml;
    }

    public String getSaglasnostZaImunizaciju(String idBrojIndex) throws RuntimeException {
        String idBroj = idBrojIndex.split("_")[0];
        if (!validator.isIdValid(idBroj)) {
            String message;
            if (idBroj.length() == 13) {
                message = "Jmbg";
            } else {
                message = "Broj pasosa";
            }
            throw new BadRequestRuntimeException(message + " koji ste uneli nije validan.");
        }

        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(idBrojIndex).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + idBrojIndex + " nije pronadjena."));
        return mapper.convertToXml(saglasnostZaImunizaciju);
    }

    public void deleteSaglasnostZaImunizaciju(String idBrojIndex) {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(idBrojIndex).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + idBrojIndex + " nije pronadjena."));
        saglasnostZaImunizacijuIDao.delete(saglasnostZaImunizaciju);
        appointmentService.setFirstAppointmentToObradjen();

        String id = saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo();

        Appointment appointment = appointmentService.createAppointment(id);
        Interesovanje i = interesovanjeIModelMapper.convertToObject(interesovanjeService.getInteresovanje(id));
        emailService.notifyPatientAboutReservedAppointment(i, appointment);
    }

    public void createSaglasnostZaImunizaciju(String saglasnostXml) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(saglasnostXml);
        if (saglasnostZaImunizaciju == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        String id = saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo();
        if (digitalniSertifikatDAO.get(id).isPresent()) {
            throw new BadRequestRuntimeException("Osobi sa id-jem " + id + " je vec izdat digitalni zeleni sertifikat");
        }

        if (!extractor.extractAndSaveToRdf(saglasnostXml, "/saglasnosti"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        setLinkToThisDocument(saglasnostZaImunizaciju);

        saglasnostZaImunizacijuIDao.save(saglasnostZaImunizaciju);
    }

    public void updateSaglasnostZaImunizaciju(String saglasnostXml) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(saglasnostXml);
        if (saglasnostZaImunizaciju == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        String id = saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo();
        if (digitalniSertifikatDAO.get(id).isPresent()) {
            throw new BadRequestRuntimeException("Osobi sa id-jem " + id + " je vec izdat digitalni zeleni sertifikat");
        }


        if (saglasnostZaImunizaciju.getEvidencijaOVakcinaciji().getVakcine().getVakcina().size() == 1) {
            Appointment appointment = appointmentService.createAppointment(id);
            Interesovanje i = interesovanjeIModelMapper.convertToObject(interesovanjeService.getInteresovanje(id));
            emailService.notifyPatientAboutReservedAppointment(i, appointment);
        }

        saglasnostZaImunizacijuIDao.update(saglasnostZaImunizaciju);
    }

    private void setLinkToThisDocument(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        try {
            potvrdaOIzvrsenojVakcinacijiService.getPotvrdaOIzvrsenojVakcinaciji(
                    saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo() + "_1");
            potvrdaOIzvrsenojVakcinacijiService.setReference(saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo() + "_1",
                    saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo() + "_2");
        } catch (Exception ignored) {
            interesovanjeService.setReference(saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo(),
                    saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo() + "_1");
        }
    }

    public void setReference(String objectId, String referencedObjectId) {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(objectId).get();
        saglasnostZaImunizaciju.setRel("pred:parentTo");
        saglasnostZaImunizaciju.setHref("http://www.akatsuki.org/potvrde/" + referencedObjectId);

        saglasnostZaImunizacijuIDao.update(saglasnostZaImunizaciju);
    }

    public ByteArrayInputStream generatePdf(String idBrojIndex) {
        try {
            return pdfTransformer.generatePDF(getSaglasnostZaImunizaciju(idBrojIndex), SaglasnostZaImunizaciju.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
