package akatsuki.officialsystem.dao;

import akatsuki.officialsystem.model.users.Sluzbenik;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SluzbenikDAO implements IDao<Sluzbenik> {

    private final String collectionId = "/db/official-system/sluzbenici";
    private final DaoUtils daoUtils;
    private final IModelMapper<Sluzbenik> mapper;

    @Override
    public Optional<Sluzbenik> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        Sluzbenik korisnik = mapper.convertToObject(resourceContent);
        if (korisnik == null)
            return Optional.empty();
        return Optional.of(korisnik);
    }

    public Optional<Sluzbenik> getByEmail(String email) {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        for (String resource : resourceContent) {
            Sluzbenik korisnik = mapper.convertToObject(resource);
            if (korisnik.getEmail().equals(email)) {
                return Optional.of(korisnik);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Sluzbenik> getAll() { return null;}

    @Override
    public String save(Sluzbenik korisnik) {
        daoUtils.createResource(collectionId, korisnik, korisnik.getIdBroj() + ".xml", Sluzbenik.class);
        return korisnik.getIdBroj() + ".xml";
    }

    @Override
    public void update(Sluzbenik korisnik) {

    }

    @Override
    public void delete(Sluzbenik korisnik) {

    }
}
