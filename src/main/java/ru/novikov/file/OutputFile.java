package ru.novikov.file;

import ru.novikov.datatype.DataType;
import ru.novikov.error.ErrorHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputFile {

    private final String outPath;
    private final String prefix;
    private final boolean appendMode;
    private final ErrorHandler errorHandler;

    public OutputFile(String outPath, String prefix, boolean appendMode, ErrorHandler errorHandler) {
        this.outPath = outPath;
        this.prefix = prefix;
        this.appendMode = appendMode;
        this.errorHandler = errorHandler;
    }

    public boolean writeToFile(DataType type, List<String> data) {
        File file = new File(getFileName(type));
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            try {
                if (!parentDir.mkdirs()) {
                    errorHandler.handleError("Не удалось создать директорию: " + parentDir.getAbsolutePath());
                    return false;
                }
                System.out.println("Указанная директория не найдена, создана новая: " + parentDir.getAbsolutePath());
            } catch (SecurityException e) {
                errorHandler.handleError("Ошибка доступа при попытке создать директорию: " + parentDir.getAbsolutePath(), e);
                return false;
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, appendMode))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            errorHandler.handleError("Не удалось записать данные в файл: " + file, e);
            return false;
        }
    }

    public String getFileName(DataType type) {
        return outPath + File.separator + prefix + type.getType() + ".txt";
    }
}
