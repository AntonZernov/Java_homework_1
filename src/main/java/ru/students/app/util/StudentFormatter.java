package ru.students.app.util;

import ru.students.app.model.Student;

public class StudentFormatter {

    public static String averageMarkFormat(double averageMark) {
        if (averageMark >= 4.5) {
            return "Отлично";
        }
        if (averageMark >= 3.5) {
            return "Хорошо";
        }
        if (averageMark >= 3.0) {
            return "Удовлетворительно";
        }
        return "Неудовлетворительно";
    }

    public static String displayFormet(Student student) {
        return String.format("%-20s | балл: %-4.2f | зачетка: %-4s | %s",
                student.getName(),
                student.getAverageMark(),
                student.getRecordBookNumber(),
                averageMarkFormat(student.getAverageMark()));
    }
}
