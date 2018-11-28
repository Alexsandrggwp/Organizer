package dao.impl;

import dao.Dao;
import entities.Task;
import entities.TaskAmount;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DaoJAXBImpl implements Dao<Task>{

    private final static Logger LOGGER = Logger.getLogger(DaoJAXBImpl.class);

    private Marshaller marshaller;
    private Unmarshaller unMarshaller;
    private File file;

    public DaoJAXBImpl(String file) {
        try {
            JAXBContext context = JAXBContext.newInstance(TaskAmount.class);
            marshaller = context.createMarshaller();
            unMarshaller = context.createUnmarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            LOGGER.info("The object DaoJAXBImpl was successfully initialized");
        } catch (JAXBException e) {
            LOGGER.error("The object DaoJAXBImpl wasn't successfully initialized");
        }
        this.file = new File(file);
    }

    public void deleteTask(int id) {
        TaskAmount taskAmount = getRoot();
        ArrayList<Task> tasks = (ArrayList<Task>) taskAmount.getTask();
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId()==id){
                tasks.remove(tasks.get(i));
                LOGGER.info("The object Task has been removed from the list");
                i--;
            }
        }
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount was successfully loaded into the Tasks.xml file after removing the object Task from the list");
        } catch (JAXBException e) {
            LOGGER.error("The object TaskAmount did not load into the Tasks.xml file after removing the object Task from the list");
        }
    }

    public void addTask(Task task) {
        TaskAmount taskAmount = getRoot();
        int id = taskAmount.getIdCounter() + 1;
        LOGGER.info("There was a change in the ID counter due to the addition of an object to the list");
        taskAmount.setIdCounter(id);
        task.setId(id);
        taskAmount.getTask().add(task);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount has successfully loaded into the Tasks.xml file after adding a new object to the list");
        } catch (JAXBException e) {
            LOGGER.error("The TaskAmount object did not load successfully into the Tasks.xml file after adding a new object to the list");
        }
    }

    public List<Task> getAll() {
        return getRoot().getTask();
    }

    private TaskAmount getRoot(){
        try {
            TaskAmount unmarshal = (TaskAmount) unMarshaller.unmarshal(file);
            LOGGER.info("There was a successful unloading of the TaskAmount object from the Tasks.xml file");
            return unmarshal;
        } catch (JAXBException e) {
            LOGGER.error("There wasn't an unloading of the TaskAmount object from the Tasks.xml file");
            System.out.println("Не удалось загрузить список");
            return new TaskAmount();
        }
    }

    public void changeName(int id, String newName){
        TaskAmount taskAmount = getRoot();
        List<Task> tasks = taskAmount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setName(newName);
            }
        }
        taskAmount.setTask(tasks);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount has successfully loaded into the Tasks.xml file after changing name of the task");
        } catch (JAXBException e) {
            LOGGER.error("The TaskAmount object did not load successfully into the Tasks.xml file after changing name of the task");
        }
    }

    public void changeDescription(int id, String newDescription){
        TaskAmount taskAmount = getRoot();
        List<Task> tasks = taskAmount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setDescription(newDescription);
            }
        }
        taskAmount.setTask(tasks);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount has successfully loaded into the Tasks.xml file after changing name of the task");
        } catch (JAXBException e) {
            LOGGER.error("The TaskAmount object did not load successfully into the Tasks.xml file after changing name of the task");
        }
    }

    public void changeContacts(int id, String newContacts){
        TaskAmount taskAmount = getRoot();
        List<Task> tasks = taskAmount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setContacts(newContacts);
            }
        }
        taskAmount.setTask(tasks);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount has successfully loaded into the Tasks.xml file after changing name of the task");
        } catch (JAXBException e) {
            LOGGER.error("The TaskAmount object did not load successfully into the Tasks.xml file after changing name of the task");
        }
    }

    /*public void taskNotified(Task task){
        TaskAmount taskAmount = getRoot();
        List<Task> tasks = taskAmount.getTask();
        for (Task taskInAmount : tasks) {
            if(task.getId()==taskInAmount.getId()){
                taskInAmount.setNotified(true);
            }
        }
        taskAmount.setTask(tasks);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("The object TaskAmount has successfully loaded into the Tasks.xml file after setting the notified status of the task");
        } catch (JAXBException e) {
            LOGGER.error("The TaskAmount object did not load successfully into the Tasks.xml file after setting the notified status of the task");
        }
    }*/
}
