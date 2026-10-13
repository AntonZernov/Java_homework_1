package ru.students.app.service;

import ru.students.app.collection.SimpleArrayList;
import ru.students.app.model.Student;
import ru.students.app.util.StudentFormatter;
import ru.students.app.util.StudentParser;

import java.util.Comparator;
import java.util.Scanner;

public class StudentService {
    private final SimpleArrayList<Student> students = new SimpleArrayList<>();
    private final FileLoader fileLoader = new FileLoader();

    public void addManual(Scanner scanner) {
        String name = readValidated(scanner,
                "Введите имя студента:", StudentParser::parseName);
        Double averageMark = readValidated(scanner,
                "Введите среднюю оценку студента:", StudentParser::parseAverageMark);
        String recordBookNumber;
        while (true) {
            recordBookNumber = readValidated(scanner,
                    "Введите номер зачетной книжки студента:", StudentParser::parseRecordBookNumber);
            if (!recordBookNumberExists(recordBookNumber)) {
                break;
            }
            System.out.println("Студент с таким номером зачетной книжки уже существует. Введите другой номер.");
        }
        students.add(new Student(name, averageMark, recordBookNumber));
        System.out.println("Студент добавлен: " + name + ", зачетка №" + recordBookNumber +
                ". Текущее количество студентов: " + students.size() + ".");
    }

    public void addFromFile(Scanner scanner) {
        System.out.println("Введите путь к файлу:");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            System.out.println("Путь к файлу не указан.");
            return;
        }
        try {
            SimpleArrayList<Student> loadedStudents = fileLoader.load(path);
            if (loadedStudents.size() == 0) {
                System.out.println("Файл пуст.");
                return;
            }
            int skipped = 0;
            for (int i = 0; i < loadedStudents.size(); i++) {
                Student student = loadedStudents.get(i);
                if (recordBookNumberExists(student.getRecordBookNumber())) {
                    skipped++;
                    continue;
                }
                students.add(student);
            }
            System.out.println("Загружено студентов: " + (loadedStudents.size() - skipped) +
                        ". Пропущено дублей по зачетке: " + skipped + ". " +
                        "Текущее количество студентов: " + students.size() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка загрузки файла: " + e.getMessage());
        }
    }

    public void processData(Scanner scanner) {
        if (students.size() == 0) {
            System.out.println("Список студентов пуст.");
        }
        Double minAverageMark = readValidated(scanner, "Введите минимальную среднюю оценку для фильтрации:",
                Double::parseDouble);
        System.out.println("Результат обработки данных:");
        SimpleArrayList<String> filteredStudents = new SimpleArrayList<>();
        students.stream()
            .filter(student -> student.getAverageMark() >= minAverageMark)
            .sorted(Comparator.comparingDouble(Student::getAverageMark).reversed())
            .map(StudentFormatter::displayFormet)
            .forEach(filteredStudents::add);
        if (filteredStudents.size() == 0) {
            System.out.println("По указанному фильтру студенты не найдены.");
            return;
        }
        for (int i = 0; i < filteredStudents.size(); i++) {
            System.out.println(filteredStudents.get(i));
        }
    }

    public void showAll() {
        if (students.size() == 0) {
            System.out.println("Список студентов пуст.");
            return;
        }
        System.out.println("Полный список студентов:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i+1) + ". " + StudentFormatter.displayFormet(students.get(i)));
        }
    }

    public void removeByRecordBookNumber(Scanner scanner) {
        if (students.size() == 0) {
            System.out.println("Список студентов пуст.");
        }
        String recordBookNumber = readValidated(scanner,
                "Введите номер зачетной книжки студента для удаления:", StudentParser::parseRecordBookNumber);
        int index = findStudentIndexByRecordBookNumber(recordBookNumber);
        if (index < 0) {
            System.out.println("Студент с таким номером зачетной книжки не найден.");
            return;
        }
        Student removed = students.remove(index);
        System.out.println("Удален студент: \n" + StudentFormatter.displayFormet(removed));
        System.out.println("Осталось студентов: " + students.size());
    }

    private int findStudentIndexByRecordBookNumber(String recordBookNumber) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getRecordBookNumber().equals(recordBookNumber))
                return i;
        }
        return -1;
    }
    private boolean recordBookNumberExists(String recordBookNumber) {
        return findStudentIndexByRecordBookNumber(recordBookNumber) >= 0;
    }

    private <T> T readValidated(Scanner scanner, String message, Parser<T> parser) {
        while (true) {
            try {
                System.out.println(message);
                return parser.parse(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            }
        }
    }

    @FunctionalInterface
    private interface Parser<T> {
        T parse(String value);
    }
}
