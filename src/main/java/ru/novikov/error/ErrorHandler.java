package ru.novikov.error;

public class ErrorHandler {

    public void handleError(String message) {
        System.err.println("Ошибка: " + message);
    }

    public void handleError(String message, Exception e) {
        System.err.println("Ошибка: " + message);
        e.printStackTrace(System.err);
    }
}
