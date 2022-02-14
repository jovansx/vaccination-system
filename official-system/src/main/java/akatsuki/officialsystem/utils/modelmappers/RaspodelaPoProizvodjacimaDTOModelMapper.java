package akatsuki.officialsystem.utils.modelmappers;

import akatsuki.officialsystem.exceptions.BadRequestRuntimeException;
import akatsuki.officialsystem.model.vaccine.RaspodelaPoProizvodjacimaDTO;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class RaspodelaPoProizvodjacimaDTOModelMapper implements IModelMapper<RaspodelaPoProizvodjacimaDTO> {
    @Override
    public RaspodelaPoProizvodjacimaDTO convertToObject(String xmlString) {
        String pfizerBioNTechString = xmlString.split("<pfizerBioNTech>")[1].split("</pfizerBioNTech>")[0].trim();
        String sputnikVString = xmlString.split("<sputnikV>")[1].split("</sputnikV>")[0].trim();
        String sinopharmString = xmlString.split("<sinopharm>")[1].split("</sinopharm>")[0].trim();
        String astraZenecaString = xmlString.split("<astraZeneca>")[1].split("</astraZeneca>")[0].trim();
        String modernaString = xmlString.split("<moderna>")[1].split("</moderna>")[0].trim();

        int kolicinaPfizera;
        int kolicinaSputnika;
        int kolicinaSinopharma;
        int kolicinaAstre;
        int kolicinaModerne;

        try {
            kolicinaPfizera = Integer.parseInt(pfizerBioNTechString);
            kolicinaSputnika = Integer.parseInt(sputnikVString);
            kolicinaSinopharma = Integer.parseInt(sinopharmString);
            kolicinaAstre = Integer.parseInt(astraZenecaString);
            kolicinaModerne = Integer.parseInt(modernaString);
        } catch (NumberFormatException ignored) {
            throw new BadRequestRuntimeException("Not valid data sent");
        }
        return new RaspodelaPoProizvodjacimaDTO(kolicinaPfizera, kolicinaSputnika, kolicinaSinopharma, kolicinaAstre, kolicinaModerne);
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
