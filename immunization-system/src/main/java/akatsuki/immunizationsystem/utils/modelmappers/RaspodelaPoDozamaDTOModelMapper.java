package akatsuki.immunizationsystem.utils.modelmappers;

import akatsuki.immunizationsystem.model.dto.RaspodelaPoDozamaDTO;
import akatsuki.immunizationsystem.model.dto.RaspodelaPoProizvodjacimaDTO;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class RaspodelaPoDozamaDTOModelMapper implements IModelMapper<RaspodelaPoDozamaDTO> {
    @Override
    public RaspodelaPoDozamaDTO convertToObject(String xmlString) {
        return null;
    }

    @Override
    public String convertToXml(RaspodelaPoDozamaDTO object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RaspodelaPoDozamaDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ignored) {
        }
        return sw.toString();
    }
}
