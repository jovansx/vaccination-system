package akatsuki.immunizationsystem.utils;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

@Component
public class HtmlTransformer {

    public static final String HTML_FILE = "document.html";
    private static final TransformerFactory transformerFactory;
    public static String XSL_FILE;

    static {
        transformerFactory = TransformerFactory.newInstance();
    }

    public ByteArrayInputStream generateHTML(String documentXml, Class<?> classOfDocument) {
        ByteArrayInputStream retVal = null;
        File file = null;
        try {
            setXSLFile(classOfDocument);

            StreamSource transformSource = new StreamSource(ResourceUtils.getFile(XSL_FILE));
            Transformer transformer = transformerFactory.newTransformer(transformSource);
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xhtml");
            StreamSource source = new StreamSource(new ByteArrayInputStream(documentXml.getBytes()));
            file = new File(HTML_FILE);
            FileOutputStream output = new FileOutputStream(file);
            StreamResult result = new StreamResult(output);
            transformer.transform(source, result);
            retVal = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));
        } catch (Exception ignored) {
        } finally {
            assert file != null;
            file.delete();
        }
        return retVal;
    }

    private void setXSLFile(Class<?> classOfDocument) {
        if (classOfDocument.equals(Interesovanje.class))
            XSL_FILE = "classpath:static/xsl/interesovanje.xsl";
        else if (classOfDocument.equals(SaglasnostZaImunizaciju.class))
            XSL_FILE = "classpath:static/xsl/saglasnost.xsl";
        else if (classOfDocument.equals(PotvrdaOVakcinaciji.class))
            XSL_FILE = "classpath:static/xsl/potvrda.xsl";
        else if (classOfDocument.equals(ZahtevZaSertifikat.class))
            XSL_FILE = "classpath:static/xsl/zahtev.xsl";
        else
            XSL_FILE = "classpath:static/xsl/digitalni.xsl";
    }
}
