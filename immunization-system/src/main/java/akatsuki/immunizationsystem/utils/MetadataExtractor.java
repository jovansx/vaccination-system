package akatsuki.immunizationsystem.utils;

import akatsuki.immunizationsystem.config.RdfConnection;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class MetadataExtractor {

    private final RdfConnection rdf;
    private final TransformerFactory transformerFactory = new TransformerFactoryImpl();

    public boolean extractAndSaveToRdf(String xml, String path) {
        try {
            String rdfFilePath = "generated_rdf.rdf";
            xml = xml.replaceAll(" datatype=\"[a-zA-Z:]*\"", "");

            extractMetadata(new ByteArrayInputStream(xml.getBytes()), new FileOutputStream(rdfFilePath));

            Model model = ModelFactory.createDefaultModel();
            model.read(rdfFilePath);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            model.write(out, SparqlUtil.NTRIPLES);

            String sparqlUpdate = SparqlUtil.insertData(rdf.getEndpoint() + path, out.toString());

            UpdateRequest update = UpdateFactory.create(sparqlUpdate);

            UpdateProcessor processor = UpdateExecutionFactory.createRemote(update,
                    String.join("/", rdf.getEndpoint(), rdf.getDataset(), rdf.getUpdate()));
            processor.execute();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            File generatedFile = new File("generated_rdf.rdf");
            generatedFile.delete();
        }
        return false;
    }

    private void extractMetadata(InputStream in, OutputStream out) throws Exception {
        File file = ResourceUtils.getFile("classpath:static/grddl.xsl");

        Transformer transformer = transformerFactory.newTransformer(new StreamSource(file));
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamSource source = new StreamSource(in);
        StreamResult result = new StreamResult(out);

        transformer.transform(source, result);
    }

    public int countTripletsFromRdf(String path) {
        String sparqlQuery = SparqlUtil.countData(rdf.getEndpoint().trim() + path, "?s ?p ?o");
        String queryEndpoint = String.join("/", rdf.getEndpoint().trim(), rdf.getDataset().trim(), rdf.getQuery().trim());

        QueryExecution query = QueryExecutionFactory.sparqlService(queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();

        int documentsNumber = 0;
        if(results.hasNext()) {
            QuerySolution querySolution = results.next() ;
            Iterator<String> variableBindings = querySolution.varNames();

            while (variableBindings.hasNext()) {

                String varName = variableBindings.next();
                RDFNode varValue = querySolution.get(varName);
                String count = varValue.asLiteral().getString();
                documentsNumber = Integer.parseInt(count);
            }
        }

        query.close();
        return documentsNumber;
    }

    public boolean readFromRdf(String path) {

        String queryEndpoint = String.join("/", rdf.getEndpoint().trim(), rdf.getDataset().trim(), rdf.getQuery().trim());
        String sparqlQuery = SparqlUtil.selectData(rdf.getEndpoint().trim() + path, "?s ?p ?o");

        // Create a QueryExecution that will access a SPARQL service over HTTP
        QueryExecution query = QueryExecutionFactory.sparqlService(queryEndpoint, sparqlQuery);

        // Query the SPARQL endpoint, iterate over the result set...
        ResultSet results = query.execSelect();

        String varName;
        RDFNode varValue;
        while(results.hasNext()) {

            // A single answer from a SELECT query
            QuerySolution querySolution = results.next() ;
            Iterator<String> variableBindings = querySolution.varNames();

            // Retrieve variable bindings
            while (variableBindings.hasNext()) {

                varName = variableBindings.next();
                varValue = querySolution.get(varName);

                System.out.println(varName + ": " + varValue);
            }
            System.out.println();
        }

        query.close() ;

        System.out.println("[INFO] End.");
        return true;
    }
}
