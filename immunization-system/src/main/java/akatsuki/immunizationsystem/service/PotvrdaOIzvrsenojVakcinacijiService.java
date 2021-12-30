package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PotvrdaOIzvrsenojVakcinacijiService {
    private final IDao<PotvrdaOVakcinaciji> potvrdaOVakcinacijiIDao;
    private final Validator validator;
    private final IModelMapper<PotvrdaOVakcinaciji> mapper;
    private final MetadataExtractor extractor;

    public String getPotvrdaOIzvrsenojVakcinaciji(String idBrojDoza) throws RuntimeException {
        if (!validator.isIdDozaValid(idBrojDoza))
            throw new BadRequestRuntimeException("Id i doza " + idBrojDoza + " nije validan.");

        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(idBrojDoza).
                orElseThrow(() -> new NotFoundRuntimeException("Nije pronadjena potvrda sa unetim " + idBrojDoza + "."));
        return mapper.convertToXml(potvrdaOVakcinaciji);
    }

    public String createPotvrdaOIzvrsenojVakcinaciji(String potvrdaOIzvrsenojVakcinacijiXml) throws RuntimeException {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = mapper.convertToObject(potvrdaOIzvrsenojVakcinacijiXml);
        if (potvrdaOVakcinaciji == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        int broj_doze = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();
        String documentId = potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_" + broj_doze;
        if (potvrdaOVakcinacijiIDao.get(documentId).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue()
                    + " i dozom broj " + broj_doze + " ima vec kreiranu potvrdu.");

        if (!extractor.extractAndSaveToRdf(potvrdaOIzvrsenojVakcinacijiXml, "/potvrde"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        return potvrdaOVakcinacijiIDao.save(potvrdaOVakcinaciji);
    }

    public void setReference(String objectId, String referencedObjectId) {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(objectId).get();
        potvrdaOVakcinaciji.setRel("pred:parentTo");
        potvrdaOVakcinaciji.setHref("http://www.akatsuki.org/saglasnosti/" + referencedObjectId);

        potvrdaOVakcinacijiIDao.save(potvrdaOVakcinaciji);
    }
}
