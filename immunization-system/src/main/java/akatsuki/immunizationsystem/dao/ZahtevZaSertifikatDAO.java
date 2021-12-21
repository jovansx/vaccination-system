package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<ZahtevZaSertifikat> getByJmbg(String id) {
        return Optional.empty();
    }

    @Override
    public Collection<ZahtevZaSertifikat> getAll() {
        return null;
    }

    @Override
    public String save(ZahtevZaSertifikat zahtevZaSertifikat) {
        String documentId = zahtevZaSertifikat.getPodnosilac().getJmbg() + "_" + UUID.randomUUID() + ".xml";
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
