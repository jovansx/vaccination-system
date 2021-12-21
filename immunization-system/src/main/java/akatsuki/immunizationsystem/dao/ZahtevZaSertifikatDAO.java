package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
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
    public Collection<ZahtevZaSertifikat> getAll() {
        return null;
    }

    @Override
    public String save(ZahtevZaSertifikat zahtevZaSertifikat) {
        return null;
    }

    @Override
    public void update(ZahtevZaSertifikat zahtevZaSertifikat) {

    }

    @Override
    public void delete(ZahtevZaSertifikat zahtevZaSertifikat) {

    }
}
