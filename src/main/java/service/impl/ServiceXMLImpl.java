package service.impl;

import dao.Dao;
import entities.Task;
import service.Service;

import java.util.List;

public class ServiceXMLImpl implements Service<Task>{

    private Dao<Task> dao;

    public ServiceXMLImpl(Dao<Task> dao) {
        this.dao = dao;
    }

    public void deleteTask(String name) {
        dao.deleteTask(name);
    }

    public void addTask(Task task) {
        dao.addTask(task);
    }

    public List<Task> getAll() {
        return dao.getAll();
    }
}
