package Main.exporter;

import Main.model.MyMatch;
import Main.model.MyUser;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.List;

public class XMLFileExporter implements FileExporter {

    @Override
    public String exportData(Object object) {
        String xmlContent = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
            if (object instanceof List) {
                jaxbContext = JAXBContext.newInstance(MyMatch.class);
            }
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            if (object instanceof List<?> list) {
                for (Object item : list) {
                    jaxbMarshaller.marshal(item, sw);
                }
            } else {
                jaxbMarshaller.marshal(object, sw);
            }
            xmlContent = sw.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlContent;
    }
}