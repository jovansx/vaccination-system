package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class VaccineModelMapper implements IModelMapper<Vaccine> {
    @Override
    public Vaccine convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vaccine.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Vaccine) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(Vaccine object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Vaccine.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();
    }
}
