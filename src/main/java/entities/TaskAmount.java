package entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class TaskAmount implements Serializable{

    private List<Task> task = new ArrayList<>();
    private int idCounter;

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> tasks) {
        this.task = tasks;
    }

    @XmlAttribute
    public int getIdCounter() {
        return idCounter;
    }

    public void setIdCounter(int idCounter) {
        this.idCounter = idCounter;
    }
}
