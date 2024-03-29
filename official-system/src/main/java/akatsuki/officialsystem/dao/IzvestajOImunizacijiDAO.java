package akatsuki.officialsystem.dao;

import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IzvestajOImunizacijiDAO implements IDao<IzvestajOImunizaciji> {

    private final String collectionId = "/db/official-system/izvestaji";
    private final DaoUtils daoUtils;
    private final IModelMapper<IzvestajOImunizaciji> mapper;

    @Override
    public Optional<IzvestajOImunizaciji> get(String id) {
        String resourceContent = daoUtils.getResource(collectionId, id);
        if (resourceContent.equals(""))
            return Optional.empty();
        IzvestajOImunizaciji izvestajOImunizaciji = mapper.convertToObject(resourceContent);
        if (izvestajOImunizaciji == null)
            return Optional.empty();
        return Optional.of(izvestajOImunizaciji);
    }

    @Override
    public Collection<IzvestajOImunizaciji> getAll() {
        List<String> resourceContent = daoUtils.getResourcesByCollectionId(collectionId);
        List<IzvestajOImunizaciji> izvestaji = new ArrayList<>();
        for (String resource : resourceContent) {
            IzvestajOImunizaciji izvestaj = mapper.convertToObject(resource);
            izvestaji.add(izvestaj);
        }
        return izvestaji;
    }

    @Override
    public String save(IzvestajOImunizaciji izvestajOImunizaciji) {
        String documentId = izvestajOImunizaciji.getPeriod().getOd() + "_" + izvestajOImunizaciji.getPeriod().get_do() + ".xml";
        daoUtils.createResource(collectionId, izvestajOImunizaciji, documentId, IzvestajOImunizaciji.class);
        return documentId.substring(0, documentId.length() - 4);
    }

    @Override
    public void update(IzvestajOImunizaciji izvestajOImunizaciji) {

    }

    @Override
    public void delete(IzvestajOImunizaciji izvestajOImunizaciji) {

    }
}
