package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    public Map<String, CensusDTO> loadCensusCountryData(String[] csvFilePath) {
        Map<String,CensusDTO> stringCensusDTOMap=super.loadCensusCountryData(USCensusData.class,csvFilePath[0]);
        return stringCensusDTOMap;
    }
}
