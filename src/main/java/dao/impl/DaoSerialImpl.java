package dao.impl;

import dao.Dao;
import entities.Task;
import entities.TaskAmount;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DaoSerialImpl implements Dao<Task> {

    private String fileName;

    public DaoSerialImpl(String fileName){
        this.fileName = fileName;
    }

    private void save(TaskAmount amount) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
                objectOutputStream.writeObject(amount);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private TaskAmount get(){
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            return (TaskAmount) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Не удалось загрузить список!!!");
            return new TaskAmount();
        }
    }

    public void deleteTask(int id) {
        TaskAmount temp = get();
        temp.setTask(temp.getTask().stream().filter( x -> x.getId() != id).collect(Collectors.toList()));
        save(temp);
    }

    public void addTask(Task task) {
        TaskAmount temp = get();
        int id = temp.getIdCounter() + 1;
        temp.setIdCounter(id);
        task.setId(id);
        temp.getTask().add(task);
        save(temp);
    }

    public List<Task> getAll() {
        return get().getTask();
    }
}
