package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.model.vaccine.RaspodelaPoDozamaDTO;
import akatsuki.officialsystem.model.vaccine.RaspodelaPoProizvodjacimaDTO;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class RaspodelaPoDozamaDTOModelMapper implements IModelMapper<RaspodelaPoDozamaDTO> {
    @Override
    public RaspodelaPoDozamaDTO convertToObject(String xmlString) {
        String doza1String = xmlString.split("<doza1>")[1].split("</doza1>")[0].trim();
        String doza2String = xmlString.split("<doza2>")[1].split("</doza2>")[0].trim();

        int kolicinaDoze1;
        int kolicinaDoze2;
        try {
            kolicinaDoze1 = Integer.parseInt(doza1String);
            kolicinaDoze2 = Integer.parseInt(doza2String);
        } catch (NumberFormatException ignored) {
            throw new BadRequestRuntimeException("Not valid data sent");
        }
        return new RaspodelaPoDozamaDTO(kolicinaDoze1, kolicinaDoze2);
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
