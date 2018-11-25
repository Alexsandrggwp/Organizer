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
            LOGGER.info("Произошла успешная инициализация объекта DaoJAXBImpl");
        } catch (JAXBException e) {
            LOGGER.error("Не Произошло успешной инициализации объекта DaoJAXBImpl");
        }
        this.file = new File(file);
    }

    public void deleteTask(int id) {
        TaskAmount taskAmount = getRoot();
        ArrayList<Task> tasks = (ArrayList<Task>) taskAmount.getTask();
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getId()==id){
                tasks.remove(tasks.get(i));
                LOGGER.info("Из списка был удален объект Task");
                i--;
            }
        }
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("Произошла успешная загрузка объекта TaskAmount в xml файл Tasks.xml после удаления объекта из списока");
        } catch (JAXBException e) {
            LOGGER.error("Не произошло успешной загрузки объекта TaskAmount в xml файл Tasks.xml после удаления объекта из списока");
        }
    }

    public void addTask(Task task) {
        TaskAmount taskAmount = getRoot();
        int id = taskAmount.getIdCounter() + 1;
        LOGGER.info("Произошло изменение счетчика айдишников всвязи с добавлением объекта в список");
        taskAmount.setIdCounter(id);
        task.setId(id);
        taskAmount.getTask().add(task);
        try {
            marshaller.marshal(taskAmount, file);
            LOGGER.info("Произошла успешная загрузка объекта TaskAmount в xml файла Tasks.xml после добавления нового объекта в список");
        } catch (JAXBException e) {
            LOGGER.error("Не произошло успешной загрузки объекта TaskAmount в xml файла Tasks.xml");
        }
    }

    public List<Task> getAll() {
        return getRoot().getTask();
    }

    private TaskAmount getRoot(){
        try {
            TaskAmount unmarshal = (TaskAmount) unMarshaller.unmarshal(file);
            LOGGER.info("Произошла успешная выгрузка объекта TaskAmount из xml файла Tasks.xml");
            return unmarshal;
        } catch (JAXBException e) {
            LOGGER.error("Не произошла успешной выгрузки объекта TaskAmount из xml файла Tasks.xml");
            System.out.println("Не удалось загрузить список");
            return new TaskAmount();
        }
    }
}
