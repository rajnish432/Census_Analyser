package censusanalyser;

public class CsvBuilderException extends RuntimeException {
    enum ExceptionType {
        UNABLE_TO_PARSE;
    }

    ExceptionType type;

    public CsvBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
