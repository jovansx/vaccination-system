package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.Collection;
import java.util.Optional;

@Component
public class InteresovanjeDAO implements Dao<Interesovanje> {

    @Autowired
    private DaoUtils daoUtils;

    private final String collectionId = "interesovanja";

    @Override
    public Optional<Interesovanje> get(String id) {
        XMLResource resource = daoUtils.getResource(collectionId, id);
        if (resource == null)
            return Optional.empty();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Interesovanje.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(resource.getContentAsDOM());
            return Optional.of(interesovanje);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Interesovanje> getAll() {
        return null;
    }

    @Override
    public String save(Interesovanje interesovanje) {
        return "";
    }

    @Override
    public void update(Interesovanje interesovanje) {

    }

    @Override
    public void delete(Interesovanje interesovanje) {

    }
}
