package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
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
    private final IDao<SaglasnostZaImunizaciju> saglasnostZaImunizacijuIDao;
    private final Validator validator;
    private final IModelMapper<SaglasnostZaImunizaciju> mapper;

    public String getInteresovanje(UUID id) throws RuntimeException {
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = saglasnostZaImunizacijuIDao.get(id.toString()).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + id + " nije pronadjena."));
        return mapper.convertToXml(saglasnostZaImunizaciju);
    }
}
