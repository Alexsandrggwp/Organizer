import UI.ConsoleUI;
import dao.impl.DaoXMLImpl;
import org.xml.sax.SAXException;
import service.impl.ServiceXMLImpl;

import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws SAXException, IOException, TransformerConfigurationException {
        DaoXMLImpl dao = new DaoXMLImpl("Tasks.xml");
        ServiceXMLImpl service = new ServiceXMLImpl(dao);
        ConsoleUI consoleUI = new ConsoleUI(System.in, service);
        consoleUI.run();
    }
}
