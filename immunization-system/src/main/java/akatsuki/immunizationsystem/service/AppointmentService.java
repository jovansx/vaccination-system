package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final IDao<Appointment> appointmentIDao;
    private final IModelMapper<Appointment> mapper;
    private final Validator validator;

    public String getCurrentAppointment() throws DatatypeConfigurationException {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        appointmentList = appointmentList.stream().filter(a -> validator.isDateBeforeAnotherDate(calendar, a.getTermin())).collect(Collectors.toList());
        return mapper.convertToXml(appointmentList.get(0));
    }

}
