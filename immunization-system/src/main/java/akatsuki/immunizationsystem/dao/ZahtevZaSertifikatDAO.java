package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

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
    public String save(ZahtevZaSertifikat zahtevZaSertifikat) {
        int index = getDocumentIndex(zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue());
        String documentId = zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue() + "_" + index + ".xml";
        daoUtils.createResource(collectionId, zahtevZaSertifikat, documentId, ZahtevZaSertifikat.class);
        return documentId;
    }

    private int getDocumentIndex(String id) {
        int index = 0;
        String fullId = id + "_" + index;
        String resourceContent = daoUtils.getResource(collectionId, fullId);
        while(!resourceContent.equals("")) {
            index++;
            fullId = id + "_" + index;
            resourceContent = daoUtils.getResource(collectionId, fullId);
        }
        return index;
    }

    @Override
    public void update(ZahtevZaSertifikat zahtevZaSertifikat) {

    }

    @Override
    public void delete(ZahtevZaSertifikat zahtevZaSertifikat) {

    }

}
