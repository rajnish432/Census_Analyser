package censusanalyser;

import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<CensusDTO> censusListDto=null;
    Map<String, CensusDTO> censusMap=null;
    List<CensusDTO> collectList=null;
    CensusLoader censusLoader = new CensusLoader();
    enum Country{
        INDIA,US;
    }

    public CensusAnalyser() {
        this.censusListDto=new ArrayList<>();
        this.censusMap=new HashMap<>();
    }

    public int loadCensusData(Country country,String... csvFilePath) {
        censusMap=censusLoader.loadCensusCountryData(country,csvFilePath);
       return censusMap.size();
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable=() -> censusCSVIterator;
        int numOfEntries=(int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedData() {
        collectList=censusMap.values().stream().collect(Collectors.toList());
        if (collectList==null || collectList.size()==0)
            {
                throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            Comparator<CensusDTO> indiaCensusCSVComparator=Comparator.comparing(census ->census.state);
            this.sort(indiaCensusCSVComparator);
            String sortedStateCensus=new Gson().toJson(collectList);
            return sortedStateCensus;
    }

    private void sort(Comparator<CensusDTO> indiaCensusDTOComparator) {
        for (int i=0;i<collectList.size()-1;i++)
        {
            for (int j=0;j<collectList.size()-i-1;j++)
            {
                CensusDTO census1= collectList.get(j);
                CensusDTO census2=collectList.get(j+1);
                if (indiaCensusDTOComparator.compare(census1,census2)>0) {
                    collectList.set(j, census2);
                    collectList.set(j + 1, census1);
                }
            }
        }
    }

    public String getStateWisePopulatedData() {
        collectList=censusMap.values().stream().collect(Collectors.toList());
        if (collectList==null || collectList.size()==0)
        {
            throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> indiaCensusDTOComparator2=Comparator.comparing(census ->census.population);
        this.sort(indiaCensusDTOComparator2);
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }

    public String getStateWiseDenseData() {
        collectList=censusMap.values().stream().collect(Collectors.toList());
        if (collectList==null || collectList.size()==0)
        {
            throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDTO> indiaCensusDTOComparator1=Comparator.comparing(census ->census.populationDensity);
        this.sort(indiaCensusDTOComparator1);
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }


}
