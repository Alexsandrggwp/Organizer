package dao.impl;

import dao.Dao;
import entities.Task;
import entities.TaskAmount;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DaoJAXBImpl implements Dao<Task>{

    private Marshaller marshaller;
    private Unmarshaller unMarshaller;
    private File file;

    public DaoJAXBImpl(String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(TaskAmount.class);
            marshaller = context.createMarshaller();
            unMarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        this.file = new File(file);
    }

    public void deleteTask(int id) {
        TaskAmount taskAmount = getRoot();
        ArrayList<Task> tasks = (ArrayList<Task>) taskAmount.getTask();
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId()==id){
                tasks.remove(tasks.get(i));
                i--;
            }
        }
        try {
            marshaller.marshal(taskAmount, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void addTask(Task task) {
        TaskAmount taskAmount = getRoot();
        int id = taskAmount.getIdCounter() + 1;
        taskAmount.setIdCounter(id);
        task.setId(id);
        taskAmount.getTask().add(task);
        try {
            marshaller.marshal(taskAmount, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getAll() {
        return getRoot().getTask();
    }

    private TaskAmount getRoot(){
        try {
            return (TaskAmount) unMarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
            System.out.println("Не удалось загрузить список");
            return new TaskAmount();
        }
    }
}
