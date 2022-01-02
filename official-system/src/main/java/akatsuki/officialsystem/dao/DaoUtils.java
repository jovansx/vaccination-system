package akatsuki.officialsystem.dao;

import akatsuki.officialsystem.config.DbConnection;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<String> getResourcesByCollectionId(String collectionId) {
        Collection col = null;
        String[] resources;
        List<String> xmlResourceList = new ArrayList<>();
        try {
            col = DatabaseManager.getCollection(dbConnection.getDbUrl() + collectionId, dbConnection.getUsername(), dbConnection.getPassword());
            col.setProperty(OutputKeys.INDENT, "yes");
            resources = col.listResources();

            for (String documentId : resources) {
                XMLResource res = (XMLResource) col.getResource(documentId);
                Object objectContent = res.getContent();
                xmlResourceList.add((String) objectContent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return xmlResourceList;
    }

    public String getResource(String collectionId, String documentId) {
        XMLResource res;
        Collection col = null;
        String responseContent = "";
        try {
            col = DatabaseManager.getCollection(dbConnection.getDbUrl() + collectionId, dbConnection.getUsername(), dbConnection.getPassword());
            col.setProperty(OutputKeys.INDENT, "yes");

            res = (XMLResource) col.getResource(documentId + ".xml");
            if (res != null)
                responseContent = (String) res.getContent();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return responseContent;
    }

    public void createResource(String collectionId, Object object, String documentId, Class<?> classOfObject) {
        try {
            Collection col = getOrCreateCollection(collectionId, 0);
            col.setProperty(OutputKeys.INDENT, "yes");
            XMLResource res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
            OutputStream os = new ByteArrayOutputStream();
            JAXBContext context = JAXBContext.newInstance(classOfObject);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, os);
            res.setContent(os);
            col.storeResource(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
        Collection col = DatabaseManager.getCollection(dbConnection.getDbUrl() + collectionUri, dbConnection.getUsername(), dbConnection.getPassword());

        if (col == null) {

            if (collectionUri.startsWith("/"))
                collectionUri = collectionUri.substring(1);

            String[] pathSegments = collectionUri.split("/");

            if (pathSegments.length > 0) {
                StringBuilder path = new StringBuilder();

                for (int i = 0; i <= pathSegmentOffset; i++)
                    path.append("/").append(pathSegments[i]);

                Collection startCol = DatabaseManager.getCollection(dbConnection.getDbUrl() + path, dbConnection.getUsername(), dbConnection.getPassword());

                if (startCol == null) {
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Collection parentCol = DatabaseManager.getCollection(dbConnection.getDbUrl() + parentPath, dbConnection.getUsername(), dbConnection.getPassword());
                    CollectionManagementService mgt = (CollectionManagementService) parentCol.getService("CollectionManagementService", "1.0");
                    col = mgt.createCollection(pathSegments[pathSegmentOffset]);
                    col.close();
                    parentCol.close();
                } else startCol.close();
            }
            return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
        } else
            return col;
    }
}
