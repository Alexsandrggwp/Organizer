package entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@XmlType(propOrder ={"name", "description", "date", "contacts"})
public class Task  implements Serializable {
    private String name;
    private String description;
    private String contacts;
    @XmlTransient
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    @XmlTransient
    private Date date;

    private boolean notified;

    private int id;

    public Task() {
    }

    public Task(String name, String description, String contacts, String strDate) {
        this.name = name;
        this.description = description;
        this.contacts = contacts;
        notified = false;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        notified = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    @XmlElement(name = "date")
    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(String date) throws ParseException {
        this.date = dateFormat.parse(date);
    }

    public Date getdDate() {
        return date;
    }

    @XmlAttribute
    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contacts='" + contacts + '\'' +
                ", date=" + dateFormat.format(date) +
                ", id=" + id +
                ", notified=" + notified +
                '}' + '\n';
    }


}
