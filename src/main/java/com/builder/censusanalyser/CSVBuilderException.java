package com.builder.censusanalyser;

public class CSVBuilderException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE, CENSUS_DELIMITER_PROBLEM, CENSUS_HEADER_PROBLEM;
    }

    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}
