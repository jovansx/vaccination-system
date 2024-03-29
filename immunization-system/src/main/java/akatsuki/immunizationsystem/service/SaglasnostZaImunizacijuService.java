package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DigitalniSertifikatDAO;
import akatsuki.immunizationsystem.dao.ISaglasnostZaImunizacijuDAO;
import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.HtmlTransformer;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.PdfTransformer;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

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
    private HtmlTransformer htmlTransformer;
    private AppointmentService appointmentService;

    private EmailService emailService;

    @Autowired
    public void setValidator(Validator validator, ISaglasnostZaImunizacijuDAO saglasnostZaImunizacijuIDao, DigitalniSertifikatDAO digitalniSertifikatDAO,
                             IModelMapper<SaglasnostZaImunizaciju> mapper, IModelMapper<Interesovanje> interesovanjeIModelMapper,
                             MetadataExtractor extractor,
                             InteresovanjeService interesovanjeService,
                             AppointmentService appointmentService, EmailService emailService,
                             @Lazy PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService, PdfTransformer pdfTransformer, HtmlTransformer htmlTransformer) {
        this.saglasnostZaImunizacijuIDao = saglasnostZaImunizacijuIDao;
        this.digitalniSertifikatDAO = digitalniSertifikatDAO;
        this.validator = validator;
        this.mapper = mapper;
        this.extractor = extractor;
        this.interesovanjeService = interesovanjeService;
        this.potvrdaOIzvrsenojVakcinacijiService = potvrdaOIzvrsenojVakcinacijiService;
        this.pdfTransformer = pdfTransformer;
        this.htmlTransformer = htmlTransformer;
        this.appointmentService = appointmentService;
        this.emailService = emailService;
        this.interesovanjeIModelMapper = interesovanjeIModelMapper;
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

        int redniBroj = this.appointmentService.getCurrentAppointmentRedniBroj();
        this.appointmentService.setCurrentObradjeno();

        String id = saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo();

        Appointment appointment = appointmentService.createAppointment(id, redniBroj);
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

        this.appointmentService.setCurrentObradjeno();

        saglasnostZaImunizacijuIDao.update(saglasnostZaImunizaciju);

        if (saglasnostZaImunizaciju.getEvidencijaOVakcinaciji().getVakcine().getVakcina().size() == 1) {
            Appointment appointment = appointmentService.createAppointment(id, 2);
            Interesovanje i = interesovanjeIModelMapper.convertToObject(interesovanjeService.getInteresovanje(id));
            emailService.notifyPatientAboutReservedAppointment(i, appointment);
        }
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

        saglasnostZaImunizacijuIDao.delete(saglasnostZaImunizaciju);
        saglasnostZaImunizacijuIDao.update(saglasnostZaImunizaciju);
    }

    public ByteArrayInputStream generatePdf(String idBrojIndex) {
        return pdfTransformer.generatePDF(getSaglasnostZaImunizaciju(idBrojIndex), SaglasnostZaImunizaciju.class);
    }

    public ByteArrayInputStream generateXhtml(String idBroj) {
        return htmlTransformer.generateHTML(getSaglasnostZaImunizaciju(idBroj), SaglasnostZaImunizaciju.class);
    }

    public MetadataDTO getMetadataJSON(String idBrojIndex) {
        return new MetadataDTO("<http://www.akatsuki.org/saglasnosti/" + idBrojIndex + ">", extractor.readFromRdfWhereObjectIs("/saglasnosti", idBrojIndex));
    }

    public String getMetadataRDF(String idBrojIndex) {
        return extractor.getRdfMetadata("/saglasnosti", idBrojIndex);
    }

    public String getSaglasnostiBySearchInput(String searchInput) {
        List<String> saglasnosti = saglasnostZaImunizacijuIDao.getAllXmls();
        StringBuilder str = new StringBuilder();
        str.append("<saglasnosti>");
        for (String i: saglasnosti) {
            if (i.contains(searchInput)  || searchInput.equals("null")) {
                String idBroj = i.split("about=\"http://www.akatsuki.org/saglasnosti/")[1]
                        .split("\"")[0];
                str.append("<idBroj>").append(idBroj).append("</idBroj>");
            }
        }
        str.append("</saglasnosti>");
        return str.toString();
    }

    public String getSaglasnostiAdvenced(String ime, String prezime, String id_broj, String lokacija, String pol) {
        String condition = "";
        if(!ime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/ime> \""+ime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!prezime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/prezime> \""+prezime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!id_broj.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> \""+id_broj+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!lokacija.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/lokacija> \""+lokacija+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!pol.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/pol> \""+pol+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(condition.equals(""))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> ?o";

        StringBuilder str = new StringBuilder();
        str.append("<saglasnosti>");
        for (String i: extractor.filterFromRdf("/saglasnosti", condition)) {
            String idBroj = i.split("http://www.akatsuki.org/saglasnosti/")[1];

            str.append("<saglasnost>");

            str.append("<idBroj>").append(idBroj).append("</idBroj>");

            try {
                SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + idBroj + " nije pronadjena."));
                str.append("<parentTo>").append(saglasnostZaImunizaciju.getHref()).append("</parentTo>");
            } catch (Exception ignored) {}

            str.append("</saglasnost>");
        }
        str.append("</saglasnosti>");
        return str.toString();
    }
}
