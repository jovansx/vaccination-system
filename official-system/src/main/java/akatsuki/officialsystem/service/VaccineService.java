package akatsuki.officialsystem.service;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.exceptions.ConflictRuntimeException;
import akatsuki.officialsystem.exceptions.NotFoundRuntimeException;
import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.model.vaccine.VaccineDTO;
import akatsuki.officialsystem.model.vaccine.VaccineType;
import akatsuki.officialsystem.model.vaccine.VaccinesDTO;
import akatsuki.officialsystem.utils.MetadataExtractor;
import akatsuki.officialsystem.utils.Validator;
import akatsuki.officialsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class VaccineService {
    private final IDao<Vaccine> vaccineIDao;
    private final IModelMapper<VaccinesDTO> vaccineDTOmapper;

    public void decreaseAmount(VaccineType vaccineType, Long serialNumber) {
        String id = vaccineType.name().toLowerCase(Locale.ROOT);
        Vaccine vaccine = vaccineIDao.get(id).get();

        for (Vaccine.Serie serie : vaccine.getSeries()) {
            if(serie.getSerialNumber() == serialNumber)
                serie.setAmount(serie.getAmount() - 1);
        }

        vaccineIDao.save(vaccine);
    }

    public void updateAmount(String xmlUpdateVaccineAmount) {
        String typeString = xmlUpdateVaccineAmount.split("<type>")[1].split("</type>")[0].trim();
        String serialNumberString = xmlUpdateVaccineAmount.split("<serialNumber>")[1].split("</serialNumber>")[0].trim();
        String amountString = xmlUpdateVaccineAmount.split("<amount>")[1].split("</amount>")[0].trim();

        long serialNumber;
        VaccineType type;
        long amount;
        try {
            serialNumber = Long.parseLong(serialNumberString);
            type = VaccineType.valueOf(typeString);
            amount = Long.parseLong(amountString);
        } catch (NumberFormatException ignored) {
            throw new BadRequestRuntimeException("Not valid data sent");
        }

        String id = type.name().toLowerCase(Locale.ROOT);
        Vaccine vaccine = vaccineIDao.get(id).get();

        for (Vaccine.Serie series : vaccine.getSeries()) {
            if(series.getSerialNumber() == serialNumber) {
                series.setAmount(amount);
                break;
            }
        }

        vaccineIDao.save(vaccine);
    }

    public String getAll() {
        List<Vaccine> vaccines = (List<Vaccine>) vaccineIDao.getAll();

        List<VaccineDTO> vaccinesDTOList = new ArrayList<>();
        for(Vaccine vaccine: vaccines) {
            for (Vaccine.Serie series : vaccine.getSeries()) {
                vaccinesDTOList.add(new VaccineDTO(vaccine.getType(), vaccine.getType().name() ,vaccine.getSideEffect(), vaccine.getManufacturer(), series.getSerialNumber(), series.getAmount()));
            }
        }
        VaccinesDTO vaccinesDTO = new VaccinesDTO(vaccinesDTOList);
        return vaccineDTOmapper.convertToXml(vaccinesDTO);
    }
}
