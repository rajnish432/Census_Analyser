package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            Iterator<IndiaCensusCSV> censusCSVIterator = getCsvFileIterator(reader,IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> csvIterable=() -> censusCSVIterator;
            int numOfEntries=(int)StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfEntries;
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> Iterator<E> getCsvFileIterator(Reader reader,Class csvClass) {
        CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
        csvToBeanBuilder.withType(csvClass);
        csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
        CsvToBean<E> csvToBean= csvToBeanBuilder.build();
        return csvToBean.iterator();
    }

    public int loadIndianStateCode(String csvFilePath) {
        try(Reader reader=Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaStateCodeData> codeDataIterator = getCsvFileIterator(reader,IndiaStateCodeData.class);
            Iterable<IndiaStateCodeData> csvIterable=() -> codeDataIterator;
            int numOfStateCode=(int)StreamSupport.stream(csvIterable.spliterator(),false).count();
            return numOfStateCode;
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
