package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    public Optional<SaglasnostZaImunizaciju> getByIdBroj(String jmbg) {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        for (String resource : resourceContent) {
            SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resource);
            if (saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo().equals(jmbg)) {
                return Optional.of(saglasnostZaImunizaciju);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<SaglasnostZaImunizaciju> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<SaglasnostZaImunizaciju> saglasnosti = new ArrayList<>();
        for (String resource : resourceContent) {
            SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resource);
            saglasnosti.add(saglasnostZaImunizaciju);
        }
        return saglasnosti;
    }

    @Override
    public String save(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        String id = saglasnostZaImunizaciju.getPacijent().getIdBrojFromDrzavljanstvo();
        int index = getDocumentIndex(id);
        daoUtils.createResource(collectionId, saglasnostZaImunizaciju, id + "_" + index + ".xml", SaglasnostZaImunizaciju.class);
        return id + "_" + index + ".xml";
    }

    private int getDocumentIndex(String id) {
        int index = 1;
        String fullId = id + "_" + index;
        String resourceContent = "";
        try {
            resourceContent = daoUtils.getResource(collectionId, fullId);

        } catch (Exception ignored) {
        }
        while (!resourceContent.equals("")) {
            index++;
            fullId = id + "_" + index;
            try {
                resourceContent = daoUtils.getResource(collectionId, fullId);

            } catch (Exception ignored) {
            }
        }
        return index;
    }

    @Override
    public void update(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        String id = saglasnostZaImunizaciju.getAbout().split("http://www.akatsuki.org/saglasnosti/")[1];
        daoUtils.createResource(collectionId, saglasnostZaImunizaciju,  id + ".xml", SaglasnostZaImunizaciju.class);
    }

    @Override
    public void delete(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }
}
