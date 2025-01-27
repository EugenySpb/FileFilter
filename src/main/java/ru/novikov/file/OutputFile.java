package ru.novikov.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OutputFile {

    private final String outPath;
    private final String prefix;
    private final boolean appendMode;

    public OutputFile(String outPath, String prefix, boolean appendMode) {
        this.outPath = outPath;
        this.prefix = prefix;
        this.appendMode = appendMode;
    }

    public void writeToFile(String type, List<String> data) throws IOException {
        String fileName = outPath + File.separator + prefix + type + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, appendMode))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public String getFileName(String type) {
        return outPath + File.separator + prefix + type + ".txt";
    }
}
