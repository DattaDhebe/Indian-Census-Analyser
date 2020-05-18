package com.censusanalyser;

public class CensusAnalyserException extends Exception {

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }

    public CensusAnalyserException(ExceptionType noFileFound, String file_not_found_in_path) {
    }

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, NO_CENSUS_DATA, INCORRECT_DELIMITER_EXCEPTION, INVALID_COUNTRY,
        INCORRECT_DELIMITER_HEADER_EXCEPTION, NO_FILE_FOUND;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }


}
