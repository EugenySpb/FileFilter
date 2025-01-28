package ru.novikov.statistics;

import ru.novikov.datatype.DataType;
import ru.novikov.file.OutputFile;
import ru.novikov.processor.DataProcessor;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.LongSummaryStatistics;

public class FullStatistics implements Statistics {

    private final OutputFile outputFile;

    public FullStatistics(OutputFile outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void calculate(DataProcessor processor) {
        System.out.println("\nПолная статистика:");
        calculateIntegers(processor.getIntegers());
        calculateFloats(processor.getFloats());
        calculateStrings(processor.getStrings());
    }

    private void calculateIntegers(List<String> integers) {
        List<Long> parseIntegers = integers.stream().map(Long::parseLong).toList();

        LongSummaryStatistics longStats = parseIntegers.stream()
                .mapToLong(Long::longValue)
                .summaryStatistics();

        long integersSum = longStats.getSum();
        long integersMin = longStats.getMin();
        long integersMax = longStats.getMax();
        double integersAverage = longStats.getAverage();

        printNumbers(DataType.INTEGERS, integers.size(), integersSum, integersMin, integersMax, integersAverage);
    }

    private void calculateFloats(List<String> floats) {
        List<Double> parseFloats = floats.stream().map(Double::parseDouble).toList();

        DoubleSummaryStatistics doubleStats = parseFloats.stream()
                .mapToDouble(Double::doubleValue)
                .summaryStatistics();

        double floatsSum = doubleStats.getSum();
        double floatsMin = doubleStats.getMin();
        double floatsMax = doubleStats.getMax();
        double floatsAverage = doubleStats.getAverage();

        printNumbers(DataType.FLOATS, floats.size(), floatsSum, floatsMin, floatsMax, floatsAverage);
    }

    private void calculateStrings(List<String> str) {
        String shortString = str.stream()
                .min(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new IllegalArgumentException("Список string пуст"));

        String longString = str.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow(() -> new IllegalArgumentException("Список string пуст"));

        System.out.println("\nФайл " + outputFile.getFileName(DataType.STRINGS) + ":");
        System.out.println("Количество элементов в файле: " + str.size());
        System.out.println("Самая короткая строка: " + shortString + " (длина: " + shortString.length() + ")");
        System.out.println("Самая длинная строка: " + longString + " (длина: " + longString.length() + ")");
    }


    private void printNumbers(DataType type, int size, Number sum, Number min, Number max, double average) {
        System.out.println("\nФайл " + outputFile.getFileName(type) + ":");
        System.out.println("Количество элементов в файле: " + size);
        System.out.println("Минимальное значение: " + min);
        System.out.println("Максимальное значение: " + max);
        System.out.println("Сумма: " + sum);
        System.out.println("Среднее значение: " + average);
    }
}
