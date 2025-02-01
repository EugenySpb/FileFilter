package ru.novikov.processor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DataProcessor {

    private final List<String> integers = new ArrayList<>();
    private final List<String> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    public void lineProcessor(String line) {
        if (isInteger(line)) {
            integers.add(line);
        } else if (isFloat(line)) {
            floats.add(line);
        } else {
            strings.add(line);
        }
    }

    public List<String> getIntegers() {
        return integers;
    }

    public List<String> getFloats() {
        return floats;
    }

    public List<String> getStrings() {
        return strings;
    }

    private boolean isInteger(String str) {
        try {
            new BigInteger(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String str) {
        try {
            new BigDecimal(str.replace(",", "."));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
