package censusanalyser;

import java.util.Map;


public class CensusAdapterFactory {
     public static Map<String,CensusDTO> getCensusData(CensusAnalyser.Country country, String... csvFilePath)
    {
        if(country.equals(CensusAnalyser.Country.INDIA))
            return new IndianCensusAdapter().loadCensusCountryData(csvFilePath);
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusCountryData(USCensusData.class, csvFilePath);
        else
            throw new CensusAnalyserException("Incorrect country",CensusAnalyserException.ExceptionType.NO_SUCH_COUNTRY);
    }
}
