package akatsuki.immunizationsystem.dao;

import akatsuki.immunizationsystem.config.DbConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XMLResource;

import javax.xml.transform.OutputKeys;

@Component
public class DaoUtils {

    @Autowired
    private DbConnection dbConnection;

    public XMLResource getResource(String collectionId, String documentId) {
        XMLResource res = null;
        try {
            Class<?> cl = Class.forName(dbConnection.getDbDriver());
            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");
            DatabaseManager.registerDatabase(database);
            Collection col = DatabaseManager.getCollection(dbConnection.getDbUrl() + collectionId, dbConnection.getUsername(), dbConnection.getPassword());
            col.setProperty(OutputKeys.INDENT, "yes");
            res = (XMLResource) col.getResource(documentId);
        } catch (Exception ignored) {
        }
        return res;
    }

}
