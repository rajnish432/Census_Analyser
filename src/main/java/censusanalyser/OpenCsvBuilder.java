package censusanalyser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.Reader;
import java.util.Iterator;

public class OpenCsvBuilder implements ICsvBuilder {
    public <E>Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean= csvToBeanBuilder.build();
        return csvToBean.iterator();
    }
}
