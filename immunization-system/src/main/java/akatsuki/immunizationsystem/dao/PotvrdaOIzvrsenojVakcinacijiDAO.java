package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PotvrdaOIzvrsenojVakcinacijiDAO implements IDao<PotvrdaOVakcinaciji> {

    private final String collectionId = "/db/vaccination-system/potvrde";
    private final DaoUtils daoUtils;
    private final IModelMapper<PotvrdaOVakcinaciji> mapper;

    @Override
    public Optional<PotvrdaOVakcinaciji> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = mapper.convertToObject(resourceContent);
        if (potvrdaOVakcinaciji == null)
            return Optional.empty();
        return Optional.of(potvrdaOVakcinaciji);
    }

    @Override
    public List<String> getAllXmls() {
        return daoUtils.getResourcesByCollectionId(collectionId);
    }

    @Override
    public Collection<PotvrdaOVakcinaciji> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<PotvrdaOVakcinaciji> potvrde = new ArrayList<>();
        for (String resource : resourceContent) {
            PotvrdaOVakcinaciji potvrda = mapper.convertToObject(resource);
            potvrde.add(potvrda);
        }
        return potvrde;
    }

    @Override
    public int getResourcesCount() {
        return 0;
    }

    @Override
    public String save(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {
        int broj_doze = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();
        String documentId = potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_" + broj_doze + ".xml";
        daoUtils.createResource(collectionId, potvrdaOVakcinaciji, documentId, PotvrdaOVakcinaciji.class);
        return documentId.substring(0, documentId.length() - 4);
    }

    @Override
    public void update(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {

    }

    @Override
    public void delete(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {

    }
}
