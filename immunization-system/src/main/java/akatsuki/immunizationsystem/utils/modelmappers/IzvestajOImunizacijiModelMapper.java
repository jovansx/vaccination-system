package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.documents.IzvestajOImunizaciji;
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
public class IzvestajOImunizacijiModelMapper implements IModelMapper<IzvestajOImunizaciji> {
    @Override
    public IzvestajOImunizaciji convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IzvestajOImunizaciji.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File file = ResourceUtils.getFile("classpath:static/xsd/izvestaj_o_imunizaciji.xsd");
            Schema schema = schemaFactory.newSchema(file);
            unmarshaller.setSchema(schema);
            return (IzvestajOImunizaciji) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(IzvestajOImunizaciji object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IzvestajOImunizaciji.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();
    }
}
