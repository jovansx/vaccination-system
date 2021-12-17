package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.DigitalniSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DigitalniSertifikatDAO implements IDao<DigitalniSertifikat> {

    private final String collectionId = "/db/vaccination-system/digitalni-sertifikati";
    private final DaoUtils daoUtils;
    private final IModelMapper<DigitalniSertifikat> mapper;

    @Override
    public Optional<DigitalniSertifikat> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        DigitalniSertifikat digitalniSertifikat = mapper.convertToObject(resourceContent);
        if (digitalniSertifikat == null)
            return Optional.empty();
        return Optional.of(digitalniSertifikat);
    }

    @Override
    public Collection<DigitalniSertifikat> getAll() {
        return null;
    }

    @Override
    public String save(DigitalniSertifikat digitalniSertifikat) {
        String documentId = digitalniSertifikat.getPrimalac().getJmbg() + ".xml";
        daoUtils.createResource(collectionId, digitalniSertifikat, documentId, DigitalniSertifikat.class);
        return digitalniSertifikat.getPrimalac().getJmbg();
    }

    @Override
    public void update(DigitalniSertifikat digitalniSertifikat) {

    }

    @Override
    public void delete(DigitalniSertifikat digitalniSertifikat) {

    }
}
