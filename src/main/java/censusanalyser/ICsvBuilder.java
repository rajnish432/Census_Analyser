package censusanalyser;

import java.io.Reader;
import java.util.Iterator;

public interface ICsvBuilder {
    public <E> Iterator<E> getCsvFileIterator(Reader reader, Class<E> csvClass);
    }
