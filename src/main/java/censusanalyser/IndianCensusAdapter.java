package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter{
    Map<String, CensusDTO> censusMap=new HashMap<>();

    @Override
    public Map<String, CensusDTO> loadCensusCountryData(String... csvFilePath) {
        Map<String, CensusDTO> stringCensusDTOMap = super.loadCensusCountryData(IndiaCensusCSV.class, csvFilePath[0]);
        this.loadIndianStateCode(censusMap,csvFilePath[1]);
        return stringCensusDTOMap;
    }

    public Map<String, CensusDTO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) {
    if(country.equals(CensusAnalyser.Country.INDIA)){
       return loadCensusData(IndiaCensusCSV.class,csvFilePath);
    }else if (country.equals(CensusAnalyser.Country.US)){
       return loadCensusData(USCensusData.class,csvFilePath);
    }
    else {
        throw new CensusAnalyserException("No Such Country",CensusAnalyserException.ExceptionType.NO_SUCH_COUNTRY);
    }
    }

    private  <E> Map<String,CensusDTO> loadCensusData(Class<E> censusCsvClass, String... csvFilePath) {

            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
                ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
                Iterator<E> censusCSVIterator = openCsvBuilder.getCsvFileIterator(reader, censusCsvClass);
                Iterable<E> iterable = () -> censusCSVIterator;
                if (censusCsvClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                    StreamSupport.stream(iterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
                }
                if (censusCsvClass.getName().equals("censusanalyser.USCensusData")) {
                    StreamSupport.stream(iterable.spliterator(), false)
                            .map(USCensusData.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
                }
                return censusMap;
            }

            catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            }
    }

        private int loadIndianStateCode(Map<String, CensusDTO> censusMap, String csvFilePath) {
        try(Reader reader=Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
            Iterator<IndiaStateCodeData> codeDataIterator = openCsvBuilder.getCsvFileIterator(reader,IndiaStateCodeData.class);
            Iterable<IndiaStateCodeData> iterableStateCode=() ->codeDataIterator;
            StreamSupport.stream(iterableStateCode.spliterator(),false)
                    .filter(csvState-> censusMap.get(csvState.stateName)!=null)
                    .forEach(csvState-> censusMap.get(csvState.stateName).state=csvState.stateCode);
            return censusMap.size();
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }



}
