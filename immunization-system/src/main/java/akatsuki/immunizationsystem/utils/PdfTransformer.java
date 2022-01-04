package akatsuki.immunizationsystem.utils;

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
import java.io.*;

@Component
public class PdfTransformer {

    private final FopFactory fopFactory;
    private final TransformerFactory transformerFactory;
    public static final String INPUT_FILE = "classpath:static/xsl-fo/saglasnost.xml";
    public static final String XSL_FILE = "classpath:static/xsl-fo/saglasnost_fo.xsl";
    public static final String OUTPUT_FILE = "src/main/resources/static/xsl-fo/saglasnost.pdf";
    public static final String FOX_XCONF = "classpath:static/xsl-fo/fop.xconf";

    public PdfTransformer() throws SAXException, IOException {
        fopFactory = FopFactory.newInstance(ResourceUtils.getFile(FOX_XCONF));
        transformerFactory = new TransformerFactoryImpl();
    }

    public void generatePDF() throws Exception {
        File xslFile = ResourceUtils.getFile(XSL_FILE);
        StreamSource transformSource = new StreamSource(xslFile);
        StreamSource source = new StreamSource(ResourceUtils.getFile(INPUT_FILE));
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
        Result res = new SAXResult(fop.getDefaultHandler());
        xslFoTransformer.transform(source, res);
        // Generate PDF file
        File pdfFile = new File(OUTPUT_FILE);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        out.write(outStream.toByteArray());
        out.close();
    }
}
