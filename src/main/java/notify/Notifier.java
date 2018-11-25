package notify;

import entities.Task;
import org.apache.log4j.Logger;
import service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notifier implements Runnable{

    private final static Logger LOGGER = Logger.getLogger(Notifier.class);

    private Service<Task> service;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E yyyy.MM.dd HH:mm");
    private ArrayList<Task> tasks;
    private Thread thread;
    private boolean isNeedToUpdate;
    private boolean isNeedToStop;

    public Notifier(Service<Task> service) {
        this.service = service;
        thread = new Thread(this, "Notifier");
        isNeedToStop = false;
        LOGGER.info("Notifier was initialized");
    }

    public void start(){
        LOGGER.info("Notifier's thread was started");
        thread.start();
    }

    private void updateList(){
        LOGGER.info("Notifiers list was updated");
        tasks = (ArrayList<Task>) service.getAll();
    }

    private void warn(Task task){
        LOGGER.info("Thr user was warned");
        System.out.println("Пришло время выполнять задачу " + task.getName());
    }

    public void run() {
        updateList();
        Date todayDate;
        while (!isNeedToStop){
            if(isNeedToUpdate){
                updateList();
                isNeedToUpdate = false;
            }
            try {
                todayDate = dateFormat.parse(dateFormat.format(new Date()));
                for(Task task: tasks){
                    if(todayDate.equals(task.getdDate()) && !task.isNotified()){
                        warn(task);
                        service.deleteTask(task.getId());
                        task.setNotified(true);
                        service.addTask(task);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNeedToUpdate() {
        this.isNeedToUpdate = true;
    }

    public void setNeedToStop(){
        this.isNeedToStop = true;
    }
}
