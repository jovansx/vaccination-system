package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.dao.ISaglasnostZaImunizacijuDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuService {
    private final ISaglasnostZaImunizacijuDAO saglasnostZaImunizacijuIDao;
    private final Validator validator;
    private final IModelMapper<SaglasnostZaImunizaciju> mapper;

    public String getInteresovanje(UUID id) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(id.toString()).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + id + " nije pronadjena."));
        return mapper.convertToXml(saglasnostZaImunizaciju);
    }

    public String createSaglasnostZaImunizaciju(String saglasnostXml) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(saglasnostXml);
        if (saglasnostZaImunizaciju == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko() == null) {
            if(saglasnostZaImunizacijuIDao.getByBrojPasosa(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getBrojPasosa()).isPresent()) {
                throw new ConflictRuntimeException("Osoba sa brojem pasosa " + saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getBrojPasosa() + " je vec podnela saglasnost za imunizaciju.");
            }
        } else {
            if(saglasnostZaImunizacijuIDao.getByJmbg(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko().getJmbg()).isPresent()) {
                throw new ConflictRuntimeException("Osoba s jmbg-om " + saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko().getJmbg() + " je vec podnela saglasnost za imunizaciju.");
            }
        }

        return saglasnostZaImunizacijuIDao.save(saglasnostZaImunizaciju);
    }
}
