package censusanalyser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCsvBuilder implements ICsvBuilder {
    @Override
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) {
      return getCSVBean(reader,csvClass).iterator();
    }

    @Override
    public <E> List<E> getCsvFileList(Reader reader, Class csvClass) {
        return getCSVBean(reader,csvClass).parse();
    }


    private <E> CsvToBean getCSVBean(Reader reader,Class csvClass)
    {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }catch (IllegalStateException e)
        {
            throw new CsvBuilderException(e.getMessage(),CsvBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
