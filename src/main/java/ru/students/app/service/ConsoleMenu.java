package ru.students.app.service;

import java.util.Scanner;

public class ConsoleMenu {
    private final StudentService studentService = new StudentService();

    public void start() {
        System.out.println("=== Приложение для работы со студентами ===");
        try (Scanner scanner = new Scanner(System.in)){
            while (true) {
                printMenu();
                int choice = readCommand(scanner);
                switch (choice) {
                    case 1 -> studentService.addManual(scanner);
                    case 2 -> studentService.addFromFile(scanner);
                    case 3 -> studentService.processData(scanner);
                    case 4 -> studentService.showAll();
                    case 5 -> studentService.removeByRecordBookNumber(scanner);
                    case 0 -> {
                        System.out.println("Завершение работы...");
                        return;
                    }
                    default -> System.out.println("Неверная команда!");
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void printMenu() {
        System.out.println("""
                === МЕНЮ ===
                  1. Добавить студента вручную
                  2. Добавить студентов из файла
                  3. Отфильтровать и отсортировать студентов
                  4. Показать всех студентов
                  5. Удалить студента по номеру зачетной книжки
                  0. Выход""");
    }

    private int readCommand(Scanner scanner) {
        System.out.println("Выберите пункт меню:");
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка. Необходимо ввести число.");
            return -1;
        }
    }
}
