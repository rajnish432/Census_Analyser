package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    Map<String, CensusDTO> censusMap=new HashMap<>();

    public <E> Map<String,CensusDTO> loadCensusData(String csvFilePath, Class<E> censusCsvClass) {

            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
                ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
                Iterator<E> censusCSVIterator = openCsvBuilder.getCsvFileIterator(reader,censusCsvClass);
                Iterable<E> iterable=() -> censusCSVIterator;
                if (censusCsvClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                    StreamSupport.stream(iterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(csvState -> censusMap.put(csvState.state, new CensusDTO(csvState)));
                }
                if (censusCsvClass.getName().equals("censusanalyser.USCensusData"))
                {
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
}
