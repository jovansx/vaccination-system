package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.IModelMapper;
import akatsuki.immunizationsystem.utils.Validator;
import org.springframework.stereotype.Service;

@Service
public class InteresovanjeService {
    private final IDao<Interesovanje> interesovanjeDAO;
    private final Validator validator;
    private final IModelMapper<Interesovanje> mapper;

    public InteresovanjeService(IDao<Interesovanje> interesovanjeDAO, Validator validator, IModelMapper<Interesovanje> modelMapper) {
        this.interesovanjeDAO = interesovanjeDAO;
        this.validator = validator;
        this.mapper = modelMapper;
    }

    public String getInteresovanje(String jmbg) throws RuntimeException {
        if (!validator.isJmbgValid(jmbg))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");

        Interesovanje interesovanje = interesovanjeDAO.get(jmbg).orElseThrow(() -> new NotFoundRuntimeException("Osoba s jmbg-om " + jmbg + " nije podnela interesovanje za vakcinacijom."));
        return mapper.convertToXml(interesovanje);
    }

    public String createInteresovanje(String interesovanjeXml) throws RuntimeException {
        Interesovanje interesovanje = mapper.convertToObject(interesovanjeXml);
        if (interesovanje == null)
            throw new BadRequestRuntimeException("Xml koji ste poslali nije validan.");

        if (interesovanjeDAO.get(interesovanje.getPodnosilac().getJmbg()).isPresent())
            throw new ConflictRuntimeException("Osoba s jmbg-om " + interesovanje.getPodnosilac().getJmbg() + " je vec podnela interesovanje za vakcinacijom.");

        return interesovanjeDAO.save(interesovanje);
    }

}
