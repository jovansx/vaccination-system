package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DigitalniSertifikatDAO;
import akatsuki.immunizationsystem.dao.ISaglasnostZaImunizacijuDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuService {
    private final ISaglasnostZaImunizacijuDAO saglasnostZaImunizacijuIDao;
    private final DigitalniSertifikatDAO digitalniSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<SaglasnostZaImunizaciju> mapper;
    private final MetadataExtractor extractor;

    public String getSaglasnostZaImunizaciju(String idBrojIndex) throws RuntimeException {
        String idBroj = idBrojIndex.split("_")[0];
        if (!validator.isIdValid(idBroj)) {
            String message;
            if(idBroj.length() == 13) {
                message = "Jmbg";
            } else {
                message = "Broj pasosa";
            }
            throw new BadRequestRuntimeException(message + " koji ste uneli nije validan.");
        }

        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(idBrojIndex).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + idBrojIndex + " nije pronadjena."));
        return mapper.convertToXml(saglasnostZaImunizaciju);
    }

    public String createSaglasnostZaImunizaciju(String saglasnostXml) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(saglasnostXml);
        if (saglasnostZaImunizaciju == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        String id;
        if(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko() == null) {
            id = saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getIdBroj().getValue();
        } else {
            id = saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko().getIdBroj().getValue();
        }
        if(digitalniSertifikatDAO.get(id).isPresent()) {
            throw new BadRequestRuntimeException("Osobi sa id-jem " + id + " je vec izdat digitalni zeleni sertifikat");
        }

        if (!extractor.extractAndSaveToRdf(saglasnostXml, "/saglasnosti"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");
        return saglasnostZaImunizacijuIDao.save(saglasnostZaImunizaciju);
    }
}
