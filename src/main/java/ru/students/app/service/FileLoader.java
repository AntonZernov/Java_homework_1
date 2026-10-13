package ru.students.app.service;

import ru.students.app.collection.SimpleArrayList;
import ru.students.app.model.Student;
import ru.students.app.util.StudentParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader {

    public SimpleArrayList<Student> load(String path) {
        Path pathToFile = Paths.get(path);
        SimpleArrayList<Student> students = new SimpleArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(pathToFile)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if ((line.isBlank())) {
                    continue;
                }
                students.add(parseLine(line));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Не удалось прочитать файл по пути: " +
                    path + "." + e.getMessage()
            );
        }
        return students;
    }

    private Student parseLine(String line) {
        String[] parts = line.split(";");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Строка должна содержать 3 поля, разделенных символом ';'.");
        }
        String name = StudentParser.parseName(parts[0]);
        double averageMark = StudentParser.parseAverageMark(parts[1]);
        String recordBookNumber = StudentParser.parseRecordBookNumber(parts[2]);
        return new Student(name, averageMark, recordBookNumber);
    }
}
