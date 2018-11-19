package UI;

import entities.Task;
import service.Service;

import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements Runnable{

    private static Scanner scanner;
    private Service<Task> service;
    private int choose;

    public ConsoleUI(InputStream in, Service<Task> service) {
        scanner = new Scanner(in);
        this.service = service;
    }

    public void run() {
        while(choose!=4) {
            System.out.println("Ведите номер желаемой операции:");
            System.out.println("1. Добавить задачу");
            System.out.println("2. Удалить задачу");
            System.out.println("3. Показать все задачи");
            System.out.println("4. Выйти");
            choose = scanner.nextInt();
            scanner.nextLine();

            switch (choose) {
                case 1: {
                    Task task = new Task();
                    System.out.println("Введите название: ");
                    task.setName(scanner.nextLine());
                    System.out.println("Введите описание: ");
                    task.setDescription(scanner.nextLine());
                    System.out.println("Введите контакты: ");
                    task.setContacts(scanner.nextLine());
                    System.out.println("Введите дату ");
                    System.out.println("ВНИМАНИЕ!!! Очень важно задать дату в правильном формате");
                    System.out.println("yyyy.MM.dd hh:mm");
                    try {
                        task.setDate(scanner.nextLine());
                    } catch (ParseException e) {
                        System.out.println("Формат даты не верен, возврат в предыдущее меню");
                        break;
                    }
                    service.addTask(task);
                    System.out.println("Задача успешно добавлена");
                    break;
                }
                case 2: {
                    System.out.println("Введите название: ");
                    service.deleteTask(scanner.nextLine());
                    System.out.println("Задача успешно удалена");
                    break;
                }
                case 3: {
                    List<Task> tasks = service.getAll();
                    System.out.println(tasks);
                    break;
                }
                case 4:{
                    System.out.println("ДАСВИДАНЬЯ");
                    break;
                }
                default:{
                    System.out.println("Вы неправильно ввели номер оперции");
                    break;
                }
            }
        }
    }
}
