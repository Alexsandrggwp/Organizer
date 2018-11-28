package dao;

import java.util.List;

public interface Dao<T> {

    void deleteTask(int id);

    void addTask(T t);

    List<T> getAll();

    void changeName(int id, String newName);

    void changeDescription(int id, String newDescription);

    void changeContacts(int id, String newContacts);
}
