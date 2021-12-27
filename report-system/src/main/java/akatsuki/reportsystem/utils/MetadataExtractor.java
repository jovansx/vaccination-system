package akatsuki.reportsystem.utils;

import akatsuki.reportsystem.config.RdfConnection;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import lombok.RequiredArgsConstructor;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
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
//            generatedFile.delete();
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
}
