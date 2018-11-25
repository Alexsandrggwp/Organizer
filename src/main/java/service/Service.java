package service;

import dao.Dao;
import entities.Task;

import java.util.List;

public interface Service<T> {

    void deleteTask(int id);

    void addTask(T t);

    List<T> getAll();

    void setDao(Dao<Task> dao);
}
