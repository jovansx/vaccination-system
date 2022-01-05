package akatsuki.immunizationsystem.utils;

import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Component
public class PdfTransformer {

    public static final String OUTPUT_FILE = "src/main/resources/static/xsl-fo/document.pdf";
    public static final String FOX_XCONF = "classpath:static/xsl-fo/fop.xconf";
    public static String XSL_FILE;
    private final FopFactory fopFactory;
    private final TransformerFactory transformerFactory;

    public PdfTransformer() throws SAXException, IOException {
        fopFactory = FopFactory.newInstance(ResourceUtils.getFile(FOX_XCONF));
        transformerFactory = new TransformerFactoryImpl();
    }

    private void setXSLFile(Class<?> classOfDocument) {
        if (classOfDocument.equals(Interesovanje.class))
            XSL_FILE = "classpath:static/xsl-fo/interesovanje_fo.xsl";
        else if (classOfDocument.equals(SaglasnostZaImunizaciju.class))
            XSL_FILE = "classpath:static/xsl-fo/saglasnost_fo.xsl";
        else if (classOfDocument.equals(PotvrdaOVakcinaciji.class))
            XSL_FILE = "classpath:static/xsl-fo/potvrda_fo.xsl";
        else if (classOfDocument.equals(ZahtevZaSertifikat.class))
            XSL_FILE = "classpath:static/xsl-fo/zahtev_fo.xsl";
        else
            XSL_FILE = "classpath:static/xsl-fo/digitalni_fo.xsl";

    }

    public ByteArrayInputStream generatePDF(String documentXml, Class<?> classOfDocument) {
        try {
            setXSLFile(classOfDocument);

            File xslFile = ResourceUtils.getFile(XSL_FILE);
            StreamSource transformSource = new StreamSource(xslFile);
            StreamSource source = new StreamSource(new ByteArrayInputStream(documentXml.getBytes()));
            FOUserAgent userAgent = fopFactory.newFOUserAgent();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
            Result res = new SAXResult(fop.getDefaultHandler());
            xslFoTransformer.transform(source, res);
            return new ByteArrayInputStream(outStream.toByteArray());
        } catch (Exception ignored) {
        }
        return null;
    }

}
