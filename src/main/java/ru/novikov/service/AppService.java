package ru.novikov.service;

import ru.novikov.datatype.DataType;
import ru.novikov.error.ErrorHandler;
import ru.novikov.file.FileProcessor;
import ru.novikov.file.OutputFile;
import ru.novikov.parser.FlagParser;
import ru.novikov.processor.DataProcessor;
import ru.novikov.statistics.FullStatistics;
import ru.novikov.statistics.ShortStatistics;

import java.util.List;

public class AppService {

    public void execute(String[] args) {
        ErrorHandler errorHandler = new ErrorHandler();
        FlagParser flag = new FlagParser(args, errorHandler);

        if (flag.getInputFiles().isEmpty()) {
            errorHandler.handleError("Не указаны входные файлы.");
            return;
        }

        OutputFile outputFile = new OutputFile(
                flag.getOutPath(),
                flag.getPrefix(),
                flag.isAppendMode(),
                errorHandler
        );

        DataProcessor processor = new DataProcessor();
        FileProcessor fileProcessor = new FileProcessor(errorHandler);

        int successCount = 0;
        int errorCount = 0;

        for (String inputFile : flag.getInputFiles()) {
            if (fileProcessor.readFile(inputFile, processor)) {
                successCount++;
            } else {
                errorHandler.handleError("Файл: " + inputFile + " не обработан. Пропускаем файл.");
                errorCount++;
            }
        }

        writeData(outputFile, processor, errorHandler);

        if (successCount > 0) {
            System.out.println("Фильтрация файлов выполнена. Результаты записаны в указанные файлы.");
        } else {
            System.out.println("Фильтрация файлов не выполнена");
        }
        System.out.println("Успешно обработано файлов: " + successCount + " | Не удалось обработать: " + errorCount);

        if (flag.isShowShortStatistics()) {
            ShortStatistics shortStatistics = new ShortStatistics(outputFile);
            shortStatistics.calculate(processor);
        }
        if (flag.isShowFullStatistics()) {
            FullStatistics fullStatistics = new FullStatistics(outputFile, errorHandler);
            fullStatistics.calculate(processor);
        }
    }

    private void writeData(OutputFile outputFile, DataProcessor processor, ErrorHandler errorHandler) {
        writeDataToFile(outputFile, processor.getIntegers(), DataType.INTEGERS, "чисел (integers)", errorHandler);
        writeDataToFile(outputFile, processor.getFloats(), DataType.FLOATS, "чисел с плавающей точкой (floats)", errorHandler);
        writeDataToFile(outputFile, processor.getStrings(), DataType.STRINGS, "строк (strings)", errorHandler);
    }

    private void writeDataToFile(OutputFile outputFile, List<String> data, DataType type, String description, ErrorHandler errorHandler) {
        if (!data.isEmpty()) {
            if (!outputFile.writeToFile(type, data)) {
                errorHandler.handleError("Не удалось записать данные в файл для " + description + ".");
            }
        }
    }
}
