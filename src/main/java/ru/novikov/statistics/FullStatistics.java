package ru.novikov.statistics;

import ru.novikov.datatype.DataType;
import ru.novikov.error.ErrorHandler;
import ru.novikov.file.OutputFile;
import ru.novikov.processor.DataProcessor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

public class FullStatistics implements Statistics {

    private final OutputFile outputFile;
    private final ErrorHandler errorHandler;

    public FullStatistics(OutputFile outputFile, ErrorHandler errorHandler) {
        this.outputFile = outputFile;
        this.errorHandler = errorHandler;
    }

    @Override
    public void calculate(DataProcessor processor) {
        System.out.println("\nПолная статистика:");
        processList(processor.getIntegers(), DataType.INTEGERS);
        processList(processor.getFloats(), DataType.FLOATS);
        processList(processor.getStrings(), DataType.STRINGS);
    }

    private void processList(List<String> data, DataType type) {
        if (!data.isEmpty()) {
            switch (type) {
                case INTEGERS:
                    calculateIntegers(data);
                    break;
                case FLOATS:
                    calculateFloats(data);
                    break;
                case STRINGS:
                    calculateStrings(data);
                    break;
            }
        } else {
            System.out.println("\nСписок " + type.getType() + " пуст. Статистика не может быть рассчитана.");
        }
    }

    private void calculateIntegers(List<String> integers) {
        try {
            List<BigInteger> parseIntegers = integers.stream().map(BigInteger::new).toList();

            BigInteger integersSum = parseIntegers.stream().reduce(BigInteger.ZERO, BigInteger::add);
            BigInteger integersMin = parseIntegers.stream().min(Comparator.naturalOrder()).orElse(BigInteger.ZERO);
            BigInteger integersMax = parseIntegers.stream().max(Comparator.naturalOrder()).orElse(BigInteger.ZERO);
            BigDecimal integersAverage = new BigDecimal(integersSum)
                    .divide(new BigDecimal(parseIntegers.size()), RoundingMode.HALF_UP);

            printNumbers(DataType.INTEGERS, integers.size(), integersSum, integersMin, integersMax, integersAverage);
        } catch (NumberFormatException e) {
            errorHandler.handleError("Ошибка при преобразовании строк в числа (integers).", e);
        }
    }

    private void calculateFloats(List<String> floats) {
        try {
            List<BigDecimal> parseFloats = floats.stream().map(str -> str.replace(",", "."))
                    .map(BigDecimal::new).toList();

            BigDecimal floatsSum = parseFloats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal floatsMin = parseFloats.stream().min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
            BigDecimal floatsMax = parseFloats.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
            BigDecimal floatsAverage = floatsSum
                    .divide(new BigDecimal(parseFloats.size()), RoundingMode.HALF_UP);

            printNumbers(DataType.FLOATS, floats.size(), floatsSum, floatsMin, floatsMax, floatsAverage);
        } catch (NumberFormatException e) {
            errorHandler.handleError("Ошибка при преобразовании строк в числа (floats).", e);
        }
    }

    private void calculateStrings(List<String> str) {
        try {
            int shortString = str.stream()
                    .min(Comparator.comparingInt(String::length))
                    .map(String::length)
                    .orElseThrow(() -> new IllegalArgumentException("Список string пуст"));

            int longString = str.stream()
                    .max(Comparator.comparingInt(String::length))
                    .map(String::length)
                    .orElseThrow(() -> new IllegalArgumentException("Список string пуст"));

            System.out.println("\nФайл " + outputFile.getFileName(DataType.STRINGS) + ":");
            System.out.println("Количество элементов в файле: " + str.size());
            System.out.println("Размер самой короткой строки: " + shortString);
            System.out.println("Размер самой длинной строки: " + longString);
        } catch (Exception e) {
            errorHandler.handleError("Ошибка при расчете статистики для строк.", e);
        }
    }


    private void printNumbers(DataType type, int size, Number sum, Number min, Number max, BigDecimal average) {
        System.out.println("\nФайл " + outputFile.getFileName(type) + ":");
        System.out.println("Количество элементов в файле: " + size);
        System.out.println("Минимальное значение: " + min);
        System.out.println("Максимальное значение: " + max);
        System.out.println("Сумма: " + sum);
        System.out.println("Среднее значение: " + average);
    }
}
