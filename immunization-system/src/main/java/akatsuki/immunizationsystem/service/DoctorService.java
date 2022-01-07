package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.users.Doktor;
import akatsuki.immunizationsystem.model.users.Korisnik;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final IDao<Korisnik> korisnikIDao;
    private final IModelMapper<Korisnik> mapper;


    public String getBasicDocktorInfo(String doctorId) {
        Doktor doktor = (Doktor) korisnikIDao.get(doctorId).get();
        return mapper.convertToXml(doktor);
    }
}
