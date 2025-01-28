package ru.novikov.statistics;

import ru.novikov.datatype.DataType;
import ru.novikov.file.OutputFile;
import ru.novikov.processor.DataProcessor;

public class ShortStatistics implements Statistics {

    private final OutputFile outputFile;

    public ShortStatistics(OutputFile outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void calculate(DataProcessor processor) {
        System.out.println("\nКраткая статистика:");
        printShortStatistics(DataType.INTEGERS, processor.getIntegers().size());
        printShortStatistics(DataType.FLOATS, processor.getFloats().size());
        printShortStatistics(DataType.STRINGS, processor.getStrings().size());
    }

    private void printShortStatistics(DataType type, int size) {
        System.out.println("Количество элементов в файле " + outputFile.getFileName(type) + ": " + size);
    }
}
