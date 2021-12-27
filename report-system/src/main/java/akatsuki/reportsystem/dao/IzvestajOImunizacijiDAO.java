package akatsuki.reportsystem.dao;

import akatsuki.reportsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.reportsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IzvestajOImunizacijiDAO implements IDao<IzvestajOImunizaciji> {

    private final String collectionId = "/db/vaccination-system/izvestaji";
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
        return null;
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
