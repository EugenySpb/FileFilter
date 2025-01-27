package ru.novikov.service;

import ru.novikov.file.FileProcessor;
import ru.novikov.file.OutputFile;
import ru.novikov.parser.FlagParser;
import ru.novikov.processor.DataProcessor;
import ru.novikov.statistics.Calculation;

import java.io.IOException;

public class AppService {

    public void execute(String[] args) throws IOException {
        FlagParser flag = new FlagParser(args);

        if (flag.getInputFiles().isEmpty()) {
            throw new IllegalArgumentException("Не указаны входные файлы.");
        }

        OutputFile outputFile = new OutputFile(
                flag.getOutPath(),
                flag.getPrefix(),
                flag.isAppendMode()
        );

        DataProcessor processor = new DataProcessor();
        FileProcessor fileProcessor = new FileProcessor();

        for (String inputFile : flag.getInputFiles()) {
            fileProcessor.readFile(inputFile, processor);
        }

        outputFile.writeToFile("integers", processor.getIntegers());
        outputFile.writeToFile("floats", processor.getFloats());
        outputFile.writeToFile("strings", processor.getStrings());

        System.out.println("Фильтрация файлов выполнена. Результаты записаны в указанные файлы.");

        if (flag.isShowStatistics()) {
            Calculation calculation = new Calculation(outputFile);
            calculation.calculateQuantity(processor);
        }
    }
}
