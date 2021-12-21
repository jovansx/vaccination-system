package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuDAO implements ISaglasnostZaImunizacijuDAO {

    private final String collectionId = "/db/vaccination-system/saglasnosti";
    private final DaoUtils daoUtils;
    private final IModelMapper<SaglasnostZaImunizaciju> mapper;

    @Override
    public Optional<SaglasnostZaImunizaciju> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resourceContent);
        if (saglasnostZaImunizaciju == null)
            return Optional.empty();
        return Optional.of(saglasnostZaImunizaciju);
    }

    public Optional<SaglasnostZaImunizaciju> getByJmbg(String jmbg) {
        return Optional.empty();
    }

    public Optional<SaglasnostZaImunizaciju> getByBrojPasosa(String brojPasosa) {
        return Optional.empty();
    }

    @Override
    public Collection<SaglasnostZaImunizaciju> getAll() {
        return null;
    }

    @Override
    public String save(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        String documentId = UUID.randomUUID() + ".xml";
        daoUtils.createResource(collectionId, saglasnostZaImunizaciju, documentId, SaglasnostZaImunizaciju.class);
        return documentId;
    }

    @Override
    public void update(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }

    @Override
    public void delete(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }
}
