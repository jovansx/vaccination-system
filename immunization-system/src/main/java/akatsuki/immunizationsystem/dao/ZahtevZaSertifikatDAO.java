package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ZahtevZaSertifikatDAO implements IZahtevZaSertifikatDAO {
    private final String collectionId = "/db/vaccination-system/zahtevi";
    private final DaoUtils daoUtils;
    private final IModelMapper<ZahtevZaSertifikat> mapper;

    @Override
    public Optional<ZahtevZaSertifikat> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        ZahtevZaSertifikat zahtevZaSertifikat = mapper.convertToObject(resourceContent);
        if (zahtevZaSertifikat == null)
            return Optional.empty();
        return Optional.of(zahtevZaSertifikat);
    }

    @Override
    public List<String> getAllXmls() {
        return daoUtils.getResourcesByCollectionId(collectionId);
    }

    @Override
    public Optional<ZahtevZaSertifikat> getByIdBroj(String idBroj) {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        for (String resource : resourceContent) {
            ZahtevZaSertifikat zahtevZaSertifikat = mapper.convertToObject(resource);
            if (zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue().equals(idBroj)) {
                return Optional.of(zahtevZaSertifikat);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<ZahtevZaSertifikat> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<ZahtevZaSertifikat> zahtevi = new ArrayList<>();
        for (String resource : resourceContent) {
            ZahtevZaSertifikat zahtevZaSertifikat = mapper.convertToObject(resource);
            zahtevi.add(zahtevZaSertifikat);
        }
        return zahtevi;
    }

    @Override
    public int getResourcesCount() {
        return daoUtils.getResourcesCount(collectionId);
    }

    @Override
    public String save(ZahtevZaSertifikat zahtevZaSertifikat) {
        String documentId = zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue() + ".xml";
        daoUtils.createResource(collectionId, zahtevZaSertifikat, documentId, ZahtevZaSertifikat.class);
        return documentId;
    }

    @Override
    public void update(ZahtevZaSertifikat zahtevZaSertifikat) {

    }

    @Override
    public void delete(ZahtevZaSertifikat zahtevZaSertifikat) {

    }

}
