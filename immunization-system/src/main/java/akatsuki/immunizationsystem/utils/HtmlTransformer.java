package akatsuki.immunizationsystem.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileOutputStream;

@Component
public class HtmlTransformer {

    public static final String INPUT_FILE = "classpath:static/xsl/interesovanje.xml";
    public static final String XSL_FILE = "classpath:static/xsl/interesovanje.xsl";
    public static final String HTML_FILE = "interesovanje.html";
    private static final DocumentBuilderFactory documentFactory;
    private static final TransformerFactory transformerFactory;

    static {
        documentFactory = DocumentBuilderFactory.newInstance();
        documentFactory.setNamespaceAware(true);
        documentFactory.setIgnoringComments(true);
        documentFactory.setIgnoringElementContentWhitespace(true);
        transformerFactory = TransformerFactory.newInstance();
    }

    public void generateHTML() {
        try {
            StreamSource transformSource = new StreamSource(ResourceUtils.getFile(XSL_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
            DocumentBuilder builder = documentFactory.newDocumentBuilder();
            Document document = builder.parse(ResourceUtils.getFile(INPUT_FILE));
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(HTML_FILE));
            transformer.transform(source, result);
        } catch (Exception ignored) {
        }
    }
}
