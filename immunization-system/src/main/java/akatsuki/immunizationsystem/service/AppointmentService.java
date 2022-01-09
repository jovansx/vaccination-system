package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final IDao<Appointment> appointmentIDao;
    private final IModelMapper<Appointment> mapper;
    private final DaoUtils utils;
    private final DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

    public String getCurrentAppointment() {
        List<Appointment> appointmentList = (List<Appointment>) appointmentIDao.getAll();
        appointmentList = appointmentList.stream().filter(a -> !a.isObradjeno()).collect(Collectors.toList());
        if (appointmentList.size() == 0)
            return "";

//        TODO sortiraj prvo
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
        Appointment appointment = null;
        try {
            GregorianCalendar calendar = determineLastAppointment();
            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
            appointment = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), idBroj, false);
            appointmentIDao.save(appointment);
        } catch (Exception ignored) {
        }
        return appointment;
    }

    private GregorianCalendar determineLastAppointment() {
        List<String> appointments = utils.execute("//termin/text()", "/db/vaccination-system/termini");
        List<String> dates = null;

        try {
            dates = appointments.stream()
                    .sorted((a1, a2) -> {
                        GregorianCalendar c1 = convertStringToGregorianCalendar(a1);
                        GregorianCalendar c2 = convertStringToGregorianCalendar(a2);
                        return c1.compareTo(c2);
                    })
                    .collect(Collectors.toList());
        }catch (Exception ignored) {}

        return convertStringToGregorianCalendar(dates.get(dates.size() - 1));
    }

    private GregorianCalendar convertStringToGregorianCalendar(String dateString) {
        GregorianCalendar calendar = null;
        try {
            String[] parts = dateString.split(":[0-9]{2}.[0-9]{3}\\+[0-9]{2}.[0-9]{2}");
            Date date = format2.parse(parts[0]);
            calendar = new GregorianCalendar();
            calendar.setTime(date);
            return calendar;
        } catch (Exception ignored) {
        }
        return calendar;
    }

    public void setCurrentObradjeno() {
        Appointment a = mapper.convertToObject(this.getCurrentAppointment());
        a.setObradjeno(true);
        appointmentIDao.save(a);
    }
}
