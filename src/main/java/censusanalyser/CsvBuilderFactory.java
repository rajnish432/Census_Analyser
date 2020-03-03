package censusanalyser;

public class CsvBuilderFactory {
    public static ICsvBuilder getOpenCsvBuilder() {
        return new OpenCsvBuilder();
    }
}
