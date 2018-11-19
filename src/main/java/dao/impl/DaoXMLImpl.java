package dao.impl;

import dao.Dao;
import entities.Task;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaoXMLImpl implements Dao<Task> {

    private Document document;
    private Transformer transformer;
    private static DocumentBuilder builder;
    private String fileName;

    static {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public DaoXMLImpl(String fileName) throws IOException, SAXException, TransformerConfigurationException {
        this.fileName = fileName;
        document = builder.parse(fileName);
        transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    }


    public void deleteTask(String name) {
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("task");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if(node.getAttributes().getNamedItem("name").getNodeValue().equals(name)){
                node.getParentNode().removeChild(node);
                i--;
            }
        }
        try {
            save();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        Element newTask = document.createElement("task");
        newTask.setAttribute("contact", task.getContacts());
        newTask.setAttribute("name", task.getName());
        newTask.setAttribute("description", task.getDescription());
        newTask.setAttribute("date", task.getDate());
        document.getDocumentElement().appendChild(newTask);
        try {
            save();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAll(){
        List<Task> tasks = new ArrayList<Task>();
        NodeList nodeList = document.getDocumentElement().getElementsByTagName("task");
        for (int i = 0; i < nodeList.getLength(); i++){
            NamedNodeMap attributes = nodeList.item(i).getAttributes();
            tasks.add(new Task(attributes.getNamedItem("name").getNodeValue(),
                    attributes.getNamedItem("description").getNodeValue(),
                    attributes.getNamedItem("contact").getNodeValue(),
                    attributes.getNamedItem("date").getNodeValue()));
        }
        return tasks;
    }

    private void save() throws TransformerException {
        transformer.transform(new DOMSource(document), new StreamResult(fileName));
    }
}
