package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.immunizationsystem.model.users.Korisnik;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KorisnikDAO implements IDao<Korisnik> {

    private final String collectionId = "/db/vaccination-system/korisnici";
    private final DaoUtils daoUtils;
    private final IModelMapper<Korisnik> mapper;

    @Override
    public Optional<Korisnik> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        Korisnik korisnik = mapper.convertToObject(resourceContent);
        if (korisnik == null)
            return Optional.empty();
        return Optional.of(korisnik);
    }

    @Override
    public Collection<Korisnik> getAll() {
        return null;
    }

    @Override
    public String save(Korisnik korisnik) {
        daoUtils.createResource(collectionId, korisnik, korisnik.getIdBroj() + ".xml", Korisnik.class);
        return korisnik.getIdBroj() + ".xml";
    }

    @Override
    public void update(Korisnik korisnik) {

    }

    @Override
    public void delete(Korisnik korisnik) {

    }
}
