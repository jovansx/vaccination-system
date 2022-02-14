package akatsuki.immunizationsystem.utils;

import akatsuki.immunizationsystem.config.RdfConnection;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
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
import java.util.*;

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
        if (results.hasNext()) {
            QuerySolution querySolution = results.next();
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

    public HashMap<String, String> readFromRdfWhereObjectIs(String path, String subject) {
        HashMap<String, String> values = new HashMap<>();

        String queryEndpoint = String.join("/", rdf.getEndpoint().trim(), rdf.getDataset().trim(), rdf.getQuery().trim());
        String sparqlQuery = SparqlUtil.selectData(rdf.getEndpoint().trim() + path, "<http://www.akatsuki.org" + path + "/" + subject + "> ?p ?o");
        QueryExecution query = QueryExecutionFactory.sparqlService(queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        String p;
        String o;
        RDFNode predicate;
        RDFNode object;
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();

            p = variableBindings.next();
            predicate = querySolution.get(p);

            o = variableBindings.next();
            object = querySolution.get(o);

            int indexOfHttp = object.toString().indexOf("http:");
            values.put(predicate.toString(), object.toString().substring(0, indexOfHttp - 2));
        }
        query.close();
        return values;
    }

    public boolean readFromRdf(String path) {
        String queryEndpoint = String.join("/", rdf.getEndpoint().trim(), rdf.getDataset().trim(), rdf.getQuery().trim());
        String sparqlQuery = SparqlUtil.selectData(rdf.getEndpoint().trim() + path, "?s ?p ?o");
        QueryExecution query = QueryExecutionFactory.sparqlService(queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        while (results.hasNext()) {
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();
            while (variableBindings.hasNext()) {
                varName = variableBindings.next();
                varValue = querySolution.get(varName);
                System.out.println(varName + ": " + varValue);
            }
            System.out.println();
        }
        query.close();
        System.out.println("[INFO] End.");
        return true;
    }

    public String getRdfMetadata(String documentType, String idBroj) {
        HashMap<String, String> map = readFromRdfWhereObjectIs(documentType, idBroj);
        StringBuilder builder =
                new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                        "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
                        "         xmlns:pred=\"http://www.akatsuki.org" + documentType + "/predicate/\">\n" +
                        "\n" +
                        "  <rdf:Description rdf:about=\"http://www.akatsuki.org" + documentType + "/" + idBroj + "\">\n");

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String predicate = entry.getKey().split("http://www.akatsuki.org/rdf/examples/predicate/")[1];
            builder.append("\t\t<pred:").append(predicate).append(">").append(entry.getValue()).append("</pred:").append(predicate).append(">\n");
        }
        builder.append("  </rdf:Description>\n\n</rdf:RDF>");
        return builder.toString();
    }

    public List<String> filterFromRdf(String path, String condition) {
        List<String> list = new ArrayList<>();

        String queryEndpoint = String.join("/", rdf.getEndpoint().trim(), rdf.getDataset().trim(), rdf.getQuery().trim());
        String sparqlQuery = SparqlUtil.selectData(rdf.getEndpoint().trim() + path, condition);
        QueryExecution query = QueryExecutionFactory.sparqlService(queryEndpoint, sparqlQuery);
        ResultSet results = query.execSelect();
        String varName;
        RDFNode varValue;
        while (results.hasNext()) {
            int index = 0;
            QuerySolution querySolution = results.next();
            Iterator<String> variableBindings = querySolution.varNames();
            while (variableBindings.hasNext()) {
                varName = variableBindings.next();
                varValue = querySolution.get(varName);
                if(index == 0)
                    list.add(varValue.toString());
                index += 1;
            }
        }
        query.close();
        return list;
    }
}
