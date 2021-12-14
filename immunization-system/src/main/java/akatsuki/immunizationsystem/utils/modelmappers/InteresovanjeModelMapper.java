package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import org.springframework.stereotype.Component;

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
public class InteresovanjeModelMapper implements IModelMapper<Interesovanje> {
    @Override
    public Interesovanje convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Interesovanje.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("static/xsd/interesovanje.xsd"));
            unmarshaller.setSchema(schema);
            return (Interesovanje) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public String convertToXml(Interesovanje object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Interesovanje.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
