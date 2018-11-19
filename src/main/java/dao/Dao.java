package dao;

import java.util.List;

public interface Dao<T> {

    void deleteTask(String name);

    void addTask(T t);

    List<T> getAll();
}
