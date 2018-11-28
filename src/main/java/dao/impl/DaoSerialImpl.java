package dao.impl;

import dao.Dao;
import entities.Task;
import entities.TaskAmount;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DaoSerialImpl implements Dao<Task> {

    private final static Logger LOGGER = Logger.getLogger(DaoSerialImpl.class);

    private String fileName;

    public DaoSerialImpl(String fileName){
        this.fileName = fileName;
    }

    private void save(TaskAmount amount) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
                objectOutputStream.writeObject(amount);
                objectOutputStream.flush();
                LOGGER.info("The TaskAmount object was successfully saved into the serialization file");
            } catch (IOException e) {
                LOGGER.error("The TaskAmount object wasn't been saved into the serialization file");
            }
    }

    private TaskAmount get(){
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            LOGGER.info("The TaskAmount object was successfully received from the serialization file");
            return (TaskAmount) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("The TaskAmount object wasn't been received from the serialization file");
            System.out.println("Не удалось загрузить список!!!");
            return new TaskAmount();
        }
    }

    public void deleteTask(int id) {
        TaskAmount temp = get();
        temp.setTask(temp.getTask().stream().filter( x -> x.getId() != id).collect(Collectors.toList()));
        LOGGER.info("Task object was deleted by id");
        save(temp);
    }

    public void addTask(Task task) {
        TaskAmount temp = get();
        int id = temp.getIdCounter() + 1;
        temp.setIdCounter(id);
        task.setId(id);
        temp.getTask().add(task);
        LOGGER.info("Task was added to the TaskAmount list");
        save(temp);
    }

    public List<Task> getAll() {
        LOGGER.info("The hole list of tasks was gotten from the TaskAmount");
        return get().getTask();
    }

    @Override
    public void changeName(int id, String newName) {
        TaskAmount amount = get();
        List<Task> tasks = amount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setName(newName);
            }
        }
        amount.setTask(tasks);
        save(amount);
    }

    @Override
    public void changeDescription(int id, String newDescription) {
        TaskAmount amount = get();
        List<Task> tasks = amount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setDescription(newDescription);
            }
        }
        amount.setTask(tasks);
        save(amount);
    }

    @Override
    public void changeContacts(int id, String newContacts) {
        TaskAmount amount = get();
        List<Task> tasks = amount.getTask();
        for (Task taskInAmount : tasks) {
            if(id==taskInAmount.getId()){
                taskInAmount.setContacts(newContacts);
            }
        }
        amount.setTask(tasks);
        save(amount);
    }
}
