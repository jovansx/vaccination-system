package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.Dao;
import akatsuki.immunizationsystem.dao.InteresovanjeDAO;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.StringReader;

@Service
public class DocumentService {
    private final Dao<Interesovanje> interesovanjeDAO;

    public DocumentService(Dao<Interesovanje> interesovanjeDAO) {
        this.interesovanjeDAO = interesovanjeDAO;
    }

    public String getInteresovanje(String jmbg) {
//        TODO Validiraj jmbg

        Interesovanje interesovanje = interesovanjeDAO.get(jmbg).orElseThrow(() -> new RuntimeException("Osoba s jmbg-om " + jmbg + " nije podnela interesovanje za vakcinacijom."));
//        TODO prebaci interesovanje objekat u xml
        return interesovanje.toString();
    }

    public String createInteresovanje(String interesovanjeXml) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Interesovanje.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File("static/xsd/interesovanje.xsd"));
        unmarshaller.setSchema(schema);

        Interesovanje interesovanje = (Interesovanje) unmarshaller.unmarshal(new StringReader(interesovanjeXml));
        return interesovanjeDAO.save(interesovanje);
    }

}
