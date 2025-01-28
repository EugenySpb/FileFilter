package ru.novikov.parser;

import java.util.ArrayList;
import java.util.List;

public class FlagParser {

    private final List<String> inputFiles = new ArrayList<>();
    private String outPath = ".";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean showShortStatistics = false;
    private boolean showFullStatistics = false;

    public FlagParser(String[] args) {
        parseFlag(args);
    }

    private void parseFlag(String[] args) {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outPath = args[++i];
                    } else {
                        throw new IllegalArgumentException("Ошибка: Не указан путь после -o");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        throw new IllegalArgumentException("Ошибка: Не указан префикс после -p");
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
                    inputFiles.add(args[i]);
                    break;
            }
        }
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
