package notify;

import entities.Task;
import service.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notifier implements Runnable{

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
    }

    public void start(){
        thread.start();
    }

    private void updateList(){
        tasks = (ArrayList<Task>) service.getAll();
    }

    private void warn(Task task){
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
