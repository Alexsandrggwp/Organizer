package UI;

import dao.impl.DaoJAXBImpl;
import dao.impl.DaoSerialImpl;
import entities.Task;
import notify.Notifier;
import org.apache.log4j.Logger;
import service.Service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements Runnable{

    private final static Logger LOGGER = Logger.getLogger(ConsoleUI.class);

    private static Scanner scanner;
    private Service<Task> service;
    private int firstMenuChoose;
    private int secondMenuChoose = 4;
    private Thread thread;
    private Notifier notifier;

    public ConsoleUI(InputStream in, Service<Task> service) {
        scanner = new Scanner(in);
        this.service = service;
        thread = new Thread(this, "ConsoleUI");
    }

    public void start(){
        thread.start();
    }

    public void run() {
        while(firstMenuChoose!=3){
            LOGGER.info("User came to the first menu");
            System.out.println("Введите номер вида хранилища данных");
            System.out.println("1. XML файл");
            System.out.println("2. Сериализованные объекты");
            System.out.println("3. Выйти из программы");
            try {
                firstMenuChoose = scanner.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Вы ввели не цифру");
                LOGGER.error("User entered not a number");
            }
            scanner.nextLine();
            switch (firstMenuChoose){
                case 1 :{
                    LOGGER.info("User chose serialization file");
                    service.setDao(new DaoJAXBImpl("Tasks.xml"));
                    setNotifier(new Notifier(service));
                    notifier.start();
                    secondMenuChoose = 0;
                    break;
                }
                case 2:{
                    LOGGER.info("User chose xml file");
                    service.setDao(new DaoSerialImpl("task_amount"));
                    setNotifier(new Notifier(service));
                    notifier.start();
                    secondMenuChoose = 0;
                    break;
                }
                case 3:{
                    LOGGER.info("User quited");
                    secondMenuChoose = 4;
                    System.out.println("датвиданиня");
                    break;
                }
                default:{ System.out.println("Вы неправильно ввели номер оперции"); }
            }
            while (secondMenuChoose!=4){
                System.out.println("Ведите номер желаемой операции:");
                System.out.println("1. Добавить задачу");
                System.out.println("2. Удалить задачу");
                System.out.println("3. Показать все задачи");
                System.out.println("4. Выйти");
                try {
                    secondMenuChoose = scanner.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Вы ввели не цифру");
                    LOGGER.error("User entered not a number");
                }
                scanner.nextLine();
                switch (secondMenuChoose) {
                    case 1: {
                        LOGGER.info("User chose to add a task");
                        Task task = new Task();
                        System.out.println("Введите название: ");
                        task.setName(scanner.nextLine());
                        System.out.println("Введите описание: ");
                        task.setDescription(scanner.nextLine());
                        System.out.println("Введите контакты: ");
                        task.setContacts(scanner.nextLine());
                        System.out.println("Введите дату ");
                        System.out.println("ВНИМАНИЕ!!! Очень важно задать дату в правильном формате");
                        System.out.println("dd.MM.yyyy HH:mm");
                        try {
                            task.setDate(scanner.nextLine());
                        } catch (ParseException e) {
                            LOGGER.error("User entered wrong date format");
                            System.out.println("Формат даты не верен, возврат в предыдущее меню");
                            break;
                        }
                        service.addTask(task);
                        if(notifier!=null) notifier.setNeedToUpdate();
                        System.out.println("Задача успешно добавлена");
                        break;
                    }
                    case 2: {
                        LOGGER.info("User chose to delete a task");
                        System.out.println("Введите ID: ");
                        service.deleteTask(scanner.nextInt());
                        System.out.println("Задача успешно удалена");
                        break;
                    }
                    case 3: {
                        LOGGER.info("User chose to see the whole list");
                        List<Task> tasks = service.getAll();
                        System.out.println(tasks);
                        break;
                    }
                    case 4:{
                        if(notifier!=null) {notifier.setNeedToStop();}
                        break;
                    }
                    default:{ System.out.println("Вы неправильно ввели номер оперции"); }
                }
            }
        }
    }

    private void setNotifier(Notifier notifier) {
        this.notifier = notifier;
    }
}
