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
        Map<String,CensusDTO> stringCensusDTOMap=super.loadCensusCountryData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndianStateCode(stringCensusDTOMap,csvFilePath[1]);
        return stringCensusDTOMap;
    }

        private int loadIndianStateCode(Map<String, CensusDTO> censusMap, String csvFilePath) {
        try(Reader reader=Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
            Iterator<IndiaStateCodeData> codeDataIterator = openCsvBuilder.getCsvFileIterator(reader,IndiaStateCodeData.class);
            Iterable<IndiaStateCodeData> iterableStateCode=() -> codeDataIterator;
            StreamSupport.stream(iterableStateCode.spliterator(),false)
                    .filter(csvState-> censusMap.get(csvState.stateName)!=null)
                    .forEach(csvState-> censusMap.get(csvState.stateName).stateCode=csvState.stateCode);
            return censusMap.size();
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
}
