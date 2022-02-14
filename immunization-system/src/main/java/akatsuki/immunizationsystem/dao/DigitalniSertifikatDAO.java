package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.DigitalniSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public List<String> getAllXmls() {
        return daoUtils.getResourcesByCollectionId(collectionId);
    }

    @Override
    public Collection<DigitalniSertifikat> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<DigitalniSertifikat> sertifikati = new ArrayList<>();
        for (String resource : resourceContent) {
            DigitalniSertifikat sertifikat = mapper.convertToObject(resource);
            sertifikati.add(sertifikat);
        }
        return sertifikati;
    }

    @Override
    public int getResourcesCount() {
        return daoUtils.getResourcesCount(collectionId);
    }

    @Override
    public String save(DigitalniSertifikat digitalniSertifikat) {
        String documentId = digitalniSertifikat.getPrimalac().getIdBroj().getValue() + ".xml";
        daoUtils.createResource(collectionId, digitalniSertifikat, documentId, DigitalniSertifikat.class);
        return digitalniSertifikat.getPrimalac().getIdBroj().getValue();
    }

    @Override
    public void update(DigitalniSertifikat digitalniSertifikat) {

    }

    @Override
    public void delete(DigitalniSertifikat digitalniSertifikat) {

    }
}
