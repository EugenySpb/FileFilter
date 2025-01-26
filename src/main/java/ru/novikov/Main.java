package ru.novikov;

import ru.novikov.error.ErrorHandler;
import ru.novikov.service.AppService;

public class Main {
    public static void main(String[] args) {

        AppService appService = new AppService();
        ErrorHandler errorHandler = new ErrorHandler();

        try {
            appService.execute(args);
        } catch (IllegalArgumentException e) {
            errorHandler.handleError(e.getMessage());
        } catch (Exception e) {
            errorHandler.handleError("Ошибка при выполнении программы", e);
        }
    }
}