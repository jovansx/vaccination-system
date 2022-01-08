package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final IDao<Appointment> appointmentIDao;
    private final IModelMapper<Appointment> mapper;
    private final DaoUtils utils;

    public String getCurrentAppointment() {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        appointmentList = appointmentList.stream().filter(a -> !a.isObradjeno()).collect(Collectors.toList());
        if (appointmentList.size() == 0)
            return "";
        return mapper.convertToXml(appointmentList.get(0));
    }

    public void setFirstAppointmentToObradjen() {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        appointmentList = appointmentList.stream().filter(a -> !a.isObradjeno()).collect(Collectors.toList());
        Appointment appointment = appointmentList.get(0);
        appointment.setObradjeno(true);
        appointmentIDao.save(appointment);
    }

    public Appointment createAppointment(String idBroj) {
        try {
            List<String> retVal = utils.execute("(//termin/text())[last()]", "/db/vaccination-system/termini");
            String[] parts = retVal.get(0).split(":[0-9]{2}.[0-9]{3}\\+[0-9]{2}.[0-9]{2}");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date date = format2.parse(parts[0]);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
            Appointment appointment = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), idBroj, false);
            appointmentIDao.save(appointment);
            return appointment;
        } catch (Exception ignored) {
        }
        return null;
    }

    public void setCurrentObradjeno() {
        Appointment a = mapper.convertToObject(this.getCurrentAppointment());
        a.setObradjeno(true);
        appointmentIDao.save(a);
    }
}
