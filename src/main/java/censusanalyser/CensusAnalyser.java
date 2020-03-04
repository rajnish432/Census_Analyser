package censusanalyser;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList;
    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
            censusCSVList = openCsvBuilder.getCsvFileList(reader,IndiaCensusCSV.class);
            return censusCSVList.size();
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable=() -> censusCSVIterator;
        int numOfEntries=(int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEntries;
    }


    public int loadIndianStateCode(String csvFilePath) {
        try(Reader reader=Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
            Iterator<IndiaStateCodeData> codeDataIterator = openCsvBuilder.getCsvFileIterator(reader,IndiaStateCodeData.class);
            return getCount(codeDataIterator);
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    public String getStateWiseSortedData() {
            if (censusCSVList==null || censusCSVList.size()==0)
            {
                throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            Comparator<IndiaCensusCSV>indiaCensusCSVComparator=Comparator.comparing(census ->census.state);
            sort(indiaCensusCSVComparator);
            String sortedStateCensus=new Gson().toJson(censusCSVList);
            return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusCSV> indiaCensusCSVComparator) {
        for (int i=0;i<censusCSVList.size()-1;i++)
        {
            for (int j=0;j<censusCSVList.size()-i-1;j++)
            {
                IndiaCensusCSV census1= censusCSVList.get(j);
                IndiaCensusCSV census2=censusCSVList.get(j+1);
                if (indiaCensusCSVComparator.compare(census1,census2)>0){
                    censusCSVList.set(j,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
        }
    }
}
