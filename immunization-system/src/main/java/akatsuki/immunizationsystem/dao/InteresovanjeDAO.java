package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InteresovanjeDAO implements IDao<Interesovanje> {

    private final String collectionId = "/db/vaccination-system/interesovanja";
    private final DaoUtils daoUtils;
    private final IModelMapper<Interesovanje> mapper;

    @Override
    public Optional<Interesovanje> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        Interesovanje interesovanje = mapper.convertToObject(resourceContent);
        if (interesovanje == null)
            return Optional.empty();
        return Optional.of(interesovanje);
    }

    @Override
    public Collection<Interesovanje> getAll() {
        return null;
    }

    @Override
    public String save(Interesovanje interesovanje) {
        String documentId = interesovanje.getPodnosilac().getJmbg().getValue() + ".xml";
        daoUtils.createResource(collectionId, interesovanje, documentId, Interesovanje.class);
        return interesovanje.getPodnosilac().getJmbg().getValue();
    }

    @Override
    public void update(Interesovanje interesovanje) {

    }

    @Override
    public void delete(Interesovanje interesovanje) {

    }
}
