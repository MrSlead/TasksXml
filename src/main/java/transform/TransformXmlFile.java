package transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class TransformXmlFile {
    private static final Logger LOG = LoggerFactory.getLogger(TransformXmlFile.class);
    public void transformXmlFile(String xslFile, String inputFile, String outputFile, int indent) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));

            transformer.transform(
                    new StreamSource(inputFile),
                    new StreamResult(new File(outputFile)));

            String[] filePath = outputFile.split("/");
            String nameFile = filePath[filePath.length - 1];
            LOG.info(String.format("Conversion  to '%s' completed", nameFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
