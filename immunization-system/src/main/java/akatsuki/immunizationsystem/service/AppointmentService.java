package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final IDao<Appointment> appointmentIDao;
    private final IModelMapper<Appointment> mapper;

    public String getCurrentAppointment() {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        appointmentList = appointmentList.stream().filter(a -> !a.isObradjeno()).collect(Collectors.toList());
        return mapper.convertToXml(appointmentList.get(0));
    }

    public void setFirstAppointmentToObradjen() {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        appointmentList = appointmentList.stream().filter(a -> !a.isObradjeno()).collect(Collectors.toList());
        Appointment appointment = appointmentList.get(0);
        appointment.setObradjeno(true);
        appointmentIDao.save(appointment);
    }
}
