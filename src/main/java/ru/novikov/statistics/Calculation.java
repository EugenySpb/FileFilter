package ru.novikov.statistics;

import ru.novikov.file.OutputFile;
import ru.novikov.processor.DataProcessor;

public class Calculation {

    private final OutputFile outputFile;

    public Calculation(OutputFile outputFile) {
        this.outputFile = outputFile;
    }

    public void calculateQuantity(DataProcessor processor) {
        int stringsCount = processor.getStrings().size();
        int integersCount = processor.getIntegers().size();
        int floatsCount = processor.getFloats().size();

        System.out.println("Краткая статистика:");
        System.out.println("Количество элементов в файле " + outputFile.getFileName("integers") + ": " + integersCount);
        System.out.println("Количество элементов в файле " + outputFile.getFileName("floats") + ": " + floatsCount);
        System.out.println("Количество элементов в файле " + outputFile.getFileName("strings") + ": " + stringsCount);
    }
}