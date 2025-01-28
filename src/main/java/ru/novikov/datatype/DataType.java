package ru.novikov.datatype;

public enum DataType {
    INTEGERS("integers"),
    FLOATS("floats"),
    STRINGS("strings");

    private final String type;

    DataType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
