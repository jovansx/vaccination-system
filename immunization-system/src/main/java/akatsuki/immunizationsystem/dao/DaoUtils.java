package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.config.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Component
public class DaoUtils {

    private final DbConnection dbConnection;

    public DaoUtils(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
        try {
            Class<?> cl = Class.forName(dbConnection.getDbDriver());
            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
        } catch (Exception ignored) {
        }
    }

    public XMLResource getResource(String collectionId, String documentId) {
        XMLResource res = null;
        try {
            Collection col = DatabaseManager.getCollection(dbConnection.getDbUrl() + collectionId, dbConnection.getUsername(), dbConnection.getPassword());
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentId);
        } catch (Exception ignored) {
        }
        return res;
    }

    public void createResource(String collectionId, Object object, Class<?> classOfObject) {
        try {
            Collection col = DatabaseManager.getCollection(dbConnection.getUrl() + collectionId, dbConnection.getUsername(), dbConnection.getPassword());
            col.setProperty(OutputKeys.INDENT, "yes");
            XMLResource res = null;
            OutputStream os = new ByteArrayOutputStream();
            JAXBContext context = JAXBContext.newInstance(classOfObject);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, os);
            res.setContent(os);
            col.storeResource(res);
        } catch (Exception ignored) {
        }
    }
}
