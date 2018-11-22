package entities;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@XmlRootElement
public class Task {
    private String name;
    private String description;
    private String contacts;
    @XmlTransient
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    @XmlTransient
    private Date date;
    @XmlAttribute
    private boolean notified;

    public Task() {
    }

    public Task(String name, String description, String contacts,String strDate) {
        this.name = name;
        this.description = description;
        this.contacts = contacts;
        notified = false;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
    public String getStringDate() {
        return dateFormat.format(date);
    }

    @XmlElement(name = "date")
    public void setDate(String date) throws ParseException {
        this.date = dateFormat.parse(date);
    }

    public Date getDate() {
        return date;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", contacts='" + contacts + '\'' +
                ", date=" + dateFormat.format(date) +
                '}' + '\n';
    }


}
