package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.model.izvestaji.IzvestajiOImunizacijiDTO;
import akatsuki.officialsystem.model.vaccine.VaccinesDTO;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class IzvestajiDTOModelMapper implements IModelMapper<IzvestajiOImunizacijiDTO> {
    @Override
    public IzvestajiOImunizacijiDTO convertToObject(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IzvestajiOImunizacijiDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (IzvestajiOImunizacijiDTO) unmarshaller.unmarshal(new StringReader(xmlString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String convertToXml(IzvestajiOImunizacijiDTO object) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(IzvestajiOImunizacijiDTO.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, sw);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();
    }
}
