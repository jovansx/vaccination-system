package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InteresovanjeService {
    private final IDao<Interesovanje> interesovanjeDAO;
    private final Validator validator;
    private final IModelMapper<Interesovanje> mapper;

    public String getInteresovanje(String jmbg) throws RuntimeException {
        if (!validator.isJmbgValid(jmbg))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");

        Interesovanje interesovanje = interesovanjeDAO.get(jmbg).orElseThrow(() -> new NotFoundRuntimeException("Osoba s jmbg-om " + jmbg + " nije podnela interesovanje za vakcinacijom."));
        return mapper.convertToXml(interesovanje);
    }

    public String createInteresovanje(String interesovanjeXml) throws RuntimeException {
        Interesovanje interesovanje = mapper.convertToObject(interesovanjeXml);
        if (interesovanje == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (interesovanjeDAO.get(interesovanje.getPodnosilac().getJmbg().getValue()).isPresent())
            throw new ConflictRuntimeException("Osoba s jmbg-om " + interesovanje.getPodnosilac().getJmbg().getValue() + " je vec podnela interesovanje za vakcinacijom.");

        return interesovanjeDAO.save(interesovanje);
    }

}
