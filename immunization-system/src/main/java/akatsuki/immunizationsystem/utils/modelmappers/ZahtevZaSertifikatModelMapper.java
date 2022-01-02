package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
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
public class ZahtevZaSertifikatModelMapper implements IModelMapper<ZahtevZaSertifikat> {
    @Override
    public ZahtevZaSertifikat convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ZahtevZaSertifikat.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            File file = ResourceUtils.getFile("classpath:static/xsd/zahtev_za_sertifikat.xsd");
            Schema schema = schemaFactory.newSchema(file);
            unmarshaller.setSchema(schema);
            return (ZahtevZaSertifikat) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    public String convertToXml(ZahtevZaSertifikat object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ZahtevZaSertifikat.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
