package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
    public Collection<PotvrdaOVakcinaciji> getAll() {
        return null;
    }

    @Override
    public String save(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {
        int broj_doze = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();
        String documentId = potvrdaOVakcinaciji.getPrimalac().getJmbg().getValue() + "_" + broj_doze + ".xml";
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
