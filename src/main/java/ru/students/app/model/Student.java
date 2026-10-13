package ru.students.app.model;

import java.util.Objects;

/**
 * Модель студента.
 * Содержит 3 поля имя средний балл и номер зачетной книжки
 */
public class Student {
    private final String name;
    private final double averageMark;
    private final String recordBookNumber;

    public Student(String name, double averageMark, String recordBookNumber) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (averageMark < 2.0 || averageMark > 5.0) {
            throw new IllegalArgumentException("Средний балл должен быть в диапазоне от 2.0 до 5.0");
        }
        if (recordBookNumber == null || recordBookNumber.isBlank()) {
            throw new IllegalArgumentException("Номер зачетной книжки не может быть пустым");
        }
        this.name = name.trim();
        this.averageMark = averageMark;
        this.recordBookNumber = recordBookNumber;
    }

    public String getName() {
        return name;
    }
    public double getAverageMark() {
        return averageMark;
    }
    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Студент: " + name + ", средний балл: " + averageMark + ", номер зачетной книжки: " + recordBookNumber + ".";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student student)) return false;
        return Objects.equals(name, student.name) && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, recordBookNumber);
    }
}
