package akatsuki.officialsystem.service;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.exceptions.ConflictRuntimeException;
import akatsuki.officialsystem.exceptions.NotFoundRuntimeException;
import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.model.vaccine.VaccineType;
import akatsuki.officialsystem.utils.MetadataExtractor;
import akatsuki.officialsystem.utils.Validator;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class VaccineService {
    private final IDao<Vaccine> vaccineIDao;

    public void decreaseAmount(VaccineType vaccineType, Long serialNumber) {
        String id = vaccineType.name().toLowerCase(Locale.ROOT);
        Vaccine vaccine = vaccineIDao.get(id).get();

        for (Vaccine.Serie serie : vaccine.getSeries()) {
            if(serie.getSerialNumber() == serialNumber)
                serie.setAmount(serie.getAmount() - 1);
        }

        vaccineIDao.save(vaccine);
    }

}
