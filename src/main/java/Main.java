import UI.ConsoleUI;
import entities.Task;
import service.Service;
import service.impl.ServiceImpl;

public class Main{
    public static void main(String[] args){
        Service<Task> service = new ServiceImpl();
        ConsoleUI consoleUI = new ConsoleUI(System.in, service);
        consoleUI.start();
    }
}
