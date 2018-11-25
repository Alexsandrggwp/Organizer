package dao;

import java.util.List;

public interface Dao<T> {

    void deleteTask(int id);

    void addTask(T t);

    List<T> getAll();
}
