package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuDAO implements ISaglasnostZaImunizacijuDAO {

    private final String collectionId = "/db/vaccination-system/saglasnosti";
    private final DaoUtils daoUtils;
    private final IModelMapper<SaglasnostZaImunizaciju> mapper;

    @Override
    public Optional<SaglasnostZaImunizaciju> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resourceContent);
        if (saglasnostZaImunizaciju == null)
            return Optional.empty();
        return Optional.of(saglasnostZaImunizaciju);
    }

    public Optional<SaglasnostZaImunizaciju> getByIdBroj(String jmbg) {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        for(String resource: resourceContent) {
            SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resource);
            if(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko().getIdBroj().equals(jmbg)
                    || saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getIdBroj().equals(jmbg) ) {
                return Optional.of(saglasnostZaImunizaciju);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<SaglasnostZaImunizaciju> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<SaglasnostZaImunizaciju> saglasnosti = new ArrayList<>();
        for(String resource: resourceContent) {
            SaglasnostZaImunizaciju saglasnostZaImunizaciju = mapper.convertToObject(resource);
            saglasnosti.add(saglasnostZaImunizaciju);
        }
        return saglasnosti;
    }

    @Override
    public String save(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {
        String id;
        if(saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getIdBroj() != null)
            id = saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getStrano().getIdBroj();
        else
            id = saglasnostZaImunizaciju.getPacijent().getDrzavljanstvo().getSrpsko().getIdBroj();

//        TODO treba index umesto onog sranja
        daoUtils.createResource(collectionId, saglasnostZaImunizaciju, id + "_" + UUID.randomUUID() + ".xml", SaglasnostZaImunizaciju.class);
        return id + ".xml";
    }

    @Override
    public void update(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }

    @Override
    public void delete(SaglasnostZaImunizaciju saglasnostZaImunizaciju) {

    }
}
