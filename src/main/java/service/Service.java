package service;

import java.util.List;

public interface Service<T> {

    void deleteTask(String name);

    void addTask(T t);

    List<T> getAll();
}
