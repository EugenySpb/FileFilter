package ru.novikov.file;

import ru.novikov.processor.DataProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {

    public void readFile(String filePath, DataProcessor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processor.lineProcessor(line);
            }
        } catch (IOException e) {
            System.err.println("Не удалось открыть файл: " + filePath);
        }
    }
}
