package akatsuki.officialsystem.dao;

import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VaccineDAO implements IDao<Vaccine> {

    private final String collectionId = "/db/official-system/vakcine";
    private final DaoUtils daoUtils;
    private final IModelMapper<Vaccine> mapper;
    @Override
    public Optional<Vaccine> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        Vaccine vaccine = mapper.convertToObject(resourceContent);
        if (vaccine == null)
            return Optional.empty();
        return Optional.of(vaccine);
    }

    @Override
    public Collection<Vaccine> getAll() {
        return null;
    }

    @Override
    public String save(Vaccine vaccine) {
        String documentId = vaccine.getType().name().toLowerCase(Locale.ROOT) + ".xml";
        daoUtils.createResource(collectionId, vaccine, documentId, Vaccine.class);
        return documentId;
    }

    @Override
    public void update(Vaccine vaccine) {

    }

    @Override
    public void delete(Vaccine vaccine) {

    }
}
