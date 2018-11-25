package service.impl;

import dao.Dao;
import dao.impl.DaoJAXBImpl;
import entities.Task;
import service.Service;

import java.util.List;

public class ServiceImpl implements Service<Task>{

    private Dao<Task> dao;

    public void deleteTask(int id) {
        dao.deleteTask(id);
    }

    public void addTask(Task task) {
        dao.addTask(task);
    }

    public List<Task> getAll() {
        return dao.getAll();
    }

    public void setDao(Dao<Task> dao){
        this.dao = dao;
    }
}
