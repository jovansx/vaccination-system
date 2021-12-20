package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuDAO implements IDao<SaglasnostZaImunizaciju> {

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

    @Override
    public Collection<SaglasnostZaImunizaciju> getAll() {
        return null;
    }

    @Override
    public String save(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        return null;
    }

    @Override
    public void update(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }

    @Override
    public void delete(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }
}
