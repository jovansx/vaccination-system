package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
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

    public String getPotvrdaOIzvrsenojVakcinaciji(String jmbgDoza) throws RuntimeException {
        if (!validator.isJmbgDozaValid(jmbgDoza))
            throw new BadRequestRuntimeException("Jmbg i doza " + jmbgDoza + " nije validan.");

        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(jmbgDoza).
                orElseThrow(() -> new NotFoundRuntimeException("Nije pronadjena potvrda sa unetim " + jmbgDoza + "."));
        return mapper.convertToXml(potvrdaOVakcinaciji);
    }

    public String createPotvrdaOIzvrsenojVakcinaciji(String potvrdaOIzvrsenojVakcinacijiXml) throws RuntimeException {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = mapper.convertToObject(potvrdaOIzvrsenojVakcinacijiXml);
        if (potvrdaOVakcinaciji == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        int broj_doze = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();
        String documentId = potvrdaOVakcinaciji.getPrimalac().getJmbg() + "_" + broj_doze;
        if (potvrdaOVakcinacijiIDao.get(documentId).isPresent())
            throw new ConflictRuntimeException("Osoba s jmbg-om " + potvrdaOVakcinaciji.getPrimalac().getJmbg()
                    + " i dozom broj " + broj_doze + " ima vec kreiranu potvrdu.");

        return potvrdaOVakcinacijiIDao.save(potvrdaOVakcinaciji);
    }

}
