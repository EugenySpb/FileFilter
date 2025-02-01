package ru.novikov.file;

import ru.novikov.error.ErrorHandler;
import ru.novikov.processor.DataProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcessor {

    private final ErrorHandler errorHandler;

    public FileProcessor(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public boolean readFile(String filePath, DataProcessor processor) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processor.lineProcessor(line);
            }
            return true;
        } catch (IOException e) {
            errorHandler.handleError("Не удалось открыть или прочитать файл: " + filePath, e);
            return false;
        }
    }
}
