package ru.students.app.util;

public class StudentParser {

    public static String parseName(String input) {
        String name = input.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (!name.matches("[a-zA-Zа-яА-ЯЁё\\s]+")) {
            throw new IllegalArgumentException("Имя может содержать только буквы");
        }
        return name;
    }

    public static double parseAverageMark(String input) {
        final double averageMark;
        try {
            averageMark = Double.parseDouble(input.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Средний балл должен быть числом");
        }
        if (averageMark < 2.0 || averageMark > 5.0) {
            throw new IllegalArgumentException("Средний балл должен быть в диапазоне от 2.0 до 5.0");
        }
        return averageMark;
    }

    public static String parseRecordBookNumber(String input) {
        String recordBookNumber = input.trim();
        if (recordBookNumber.isEmpty()) {
            throw new IllegalArgumentException("Номер зачетной книжки не может быть пустым");
        }
        if (!recordBookNumber.matches("[0-9]+")) {
            throw new IllegalArgumentException("Номер зачетной книжки должен состоять только из цифр");
        }
        return recordBookNumber;
    }
}
