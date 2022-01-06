package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.dto.RaspodelaPoProizvodjacimaDTO;
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
public class RaspodelaPoProizvodjacimaDTOModelMapper implements IModelMapper<RaspodelaPoProizvodjacimaDTO> {
    @Override
    public RaspodelaPoProizvodjacimaDTO convertToObject(String xmlString) {
        return null;
    }

    @Override
    public String convertToXml(RaspodelaPoProizvodjacimaDTO object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RaspodelaPoProizvodjacimaDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
