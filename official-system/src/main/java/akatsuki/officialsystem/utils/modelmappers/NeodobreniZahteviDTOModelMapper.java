package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.model.zahtevi.NeodobreniZahteviDTO;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class NeodobreniZahteviDTOModelMapper implements IModelMapper<NeodobreniZahteviDTO>{

    @Override
    public NeodobreniZahteviDTO convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NeodobreniZahteviDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (NeodobreniZahteviDTO) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(NeodobreniZahteviDTO object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(NeodobreniZahteviDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();
    }
}
