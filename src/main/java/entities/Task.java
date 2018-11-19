package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String name;
    private String description;
    private String contacts;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
    private Date date;

    public Task() {
    }

    public Task(String name, String description, String contacts,String strDate) {
        this.name = name;
        this.description = description;
        this.contacts = contacts;
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

    public String getDate() {
        return dateFormat.format(date);
    }

    public void setDate(String date) throws ParseException {
        this.date = dateFormat.parse(date);
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
