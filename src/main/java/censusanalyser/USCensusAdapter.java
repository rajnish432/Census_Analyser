package censusanalyser;

import java.util.Map;

public class USCensusAdapter {
    public Map<String, CensusDTO> loadCensusCountryData(Class<USCensusData> usCensusDataClass, String[] csvFilePath) {
        Map<String,CensusDTO> stringCensusDTOMap=loadCensusCountryData(USCensusData.class,csvFilePath);
        return stringCensusDTOMap;
    }
}
