package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.modules.XMLResource;

import java.util.Collection;
import java.util.Optional;

@Component
public class InteresovanjeDao implements IDao<Interesovanje> {

    private final String collectionId = "interesovanja";
    @Autowired
    private DaoUtils daoUtils;
    @Autowired
    private IModelMapper<Interesovanje> mapper;

    @Override
    public Optional<Interesovanje> get(String id) {
        XMLResource resource = daoUtils.getResource(collectionId, id);
        if (resource == null)
            return Optional.empty();
        Interesovanje interesovanje = mapper.convertToObject(resource.toString());
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
        daoUtils.createResource(collectionId, interesovanje, Interesovanje.class);
        return interesovanje.getPodnosilac().getJmbg();
    }

    @Override
    public void update(Interesovanje interesovanje) {

    }

    @Override
    public void delete(Interesovanje interesovanje) {

    }
}
