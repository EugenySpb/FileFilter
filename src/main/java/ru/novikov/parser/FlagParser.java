package ru.novikov.parser;

import ru.novikov.error.ErrorHandler;

import java.util.*;

public class FlagParser {

    private final List<String> inputFiles = new ArrayList<>();
    private final ErrorHandler errorHandler;
    private String outPath = ".";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean showShortStatistics = false;
    private boolean showFullStatistics = false;

    public FlagParser(String[] args, ErrorHandler errorHandler1) {
        this.errorHandler = errorHandler1;
        parseFlag(args);
    }

    private void parseFlag(String[] args) {
        Map<String, String> usedFlags = new HashMap<>();
        Set<String> validFlags = Set.of("-o", "-p", "-a", "-s", "-f");

        for (int i = 0; i < args.length; i++) {
            String flag = args[i];
            switch (flag) {
                case "-o":
                case "-p":
                    if (i + 1 < args.length && !validFlags.contains(args[i + 1])) {
                        String value = args[++i];
                        if (value.endsWith(".txt")) {
                            errorHandler.handleError("Не указан аргумент после " + flag + ", используется значение по умолчанию.");
                            inputFiles.add(value);
                        } else {
                            if (usedFlags.containsKey(flag)) {
                                errorHandler.handleError("Флаг " + flag + " указан несколько раз. Повтор не учитывается.");
                            } else {
                                usedFlags.put(flag, value);
                            }
                        }
                    } else {
                        errorHandler.handleError("Не указан аргумент после " + flag + ", используется значение по умолчанию.");
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    showShortStatistics = true;
                    break;
                case "-f":
                    showFullStatistics = true;
                    break;
                default:
                    inputFiles.add(flag);
                    break;
            }
        }
        prefix = usedFlags.getOrDefault("-p", prefix);
        outPath = usedFlags.getOrDefault("-o", outPath);
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutPath() {
        return outPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isShowShortStatistics() {
        return showShortStatistics;
    }

    public boolean isShowFullStatistics() {
        return showFullStatistics;
    }
}
