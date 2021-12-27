package akatsuki.reportsystem.utils.modelmappers;

import akatsuki.reportsystem.model.users.Korisnik;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class KorisnikModelMapper implements IModelMapper<Korisnik> {
    @Override
    public Korisnik convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Korisnik.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Korisnik) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(Korisnik object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Korisnik.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
