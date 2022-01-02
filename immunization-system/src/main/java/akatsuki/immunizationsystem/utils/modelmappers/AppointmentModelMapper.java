package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.users.Korisnik;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class AppointmentModelMapper implements IModelMapper<Appointment> {
    @Override
    public Appointment convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Appointment.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Appointment) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(Appointment object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Appointment.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
