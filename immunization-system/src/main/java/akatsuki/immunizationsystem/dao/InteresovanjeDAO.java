package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.modules.XMLResource;

import java.util.Collection;
import java.util.Optional;

@Component
public class InteresovanjeDAO implements IDao<Interesovanje> {

    private final String collectionId = "/db/vaccination-system/interesovanja";
    @Autowired
    private DaoUtils daoUtils;
    @Autowired
    private IModelMapper<Interesovanje> mapper;

//    TODO Da li vracati ovaj optional ili string pa ga
//     konvertovati u servisu da ne bi 2 puta konvertovali bezveze
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
        String documentId = interesovanje.getPodnosilac().getJmbg() + ".xml";
        daoUtils.createResource(collectionId, interesovanje, documentId, Interesovanje.class);
        return interesovanje.getPodnosilac().getJmbg();
    }

    @Override
    public void update(Interesovanje interesovanje) {

    }

    @Override
    public void delete(Interesovanje interesovanje) {

    }
}
