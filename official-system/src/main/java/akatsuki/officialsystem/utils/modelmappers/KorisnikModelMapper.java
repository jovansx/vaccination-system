package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.model.users.Sluzbenik;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class KorisnikModelMapper implements IModelMapper<Sluzbenik> {
    @Override
    public Sluzbenik convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Sluzbenik.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Sluzbenik) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(Sluzbenik object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Sluzbenik.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
