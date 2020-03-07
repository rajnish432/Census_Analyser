package censusanalyser;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    List<CensusDTO> censusListDto=null;
    Map<String, CensusDTO> censusMap=null;
    List<CensusDTO> collectList=null;
    IndianCensusAdapter censusLoader = new IndianCensusAdapter();
    Map<SortField,Comparator<CensusDTO>> sortMap;

    enum Country{
        INDIA,US;
    }
    public CensusAnalyser() {
        sortMap=new HashMap<>();
        this.sortMap.put(SortField.STATE,Comparator.comparing(census-> census.state));
        this.sortMap.put(SortField.POPULATION,Comparator.comparing(census-> census.population));
        this.sortMap.put(SortField.TOTALAREA,Comparator.comparing(census->census.totalArea));
        this.sortMap.put(SortField.POPULATIONDENSITY,Comparator.comparing(census-> census.populationDensity));
        this.sortMap.put(SortField.STATECODE,Comparator.comparing(census->census.stateCode));
        this.sortMap.put(SortField.STATEID,Comparator.comparing(census-> census.stateID));
        this.censusListDto=new ArrayList<>();
        this.censusMap=new HashMap<>();
    }

    public int loadCensusDatas(Country country,String... csvFilePath) {
        censusMap=CensusAdapterFactory.getCensusData(country,csvFilePath);
       return censusMap.size();
    }

    private void sort(List<CensusDTO> collectList, Comparator<CensusDTO> sortField) {
        for (int i=0;i<collectList.size()-1;i++)
        {
            for (int j=0;j<collectList.size()-i-1;j++)
            {
                CensusDTO census1= collectList.get(j);
                CensusDTO census2=collectList.get(j+1);
                if (sortField.compare(census1,census2)>0) {
                    collectList.set(j, census2);
                    collectList.set(j + 1, census1);
                }
            }
        }
    }

    public String getSortedData(SortField sortField) {
        collectList=censusMap.values().stream().collect(Collectors.toList());
        if (collectList==null || collectList.size()==0) {
            throw new CensusAnalyserException("No such Data Found", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        this.sort(collectList,this.sortMap.get(sortField));
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }

    public String getReversedSortedData(SortField sortField) {
        collectList=censusMap.values().stream().collect(Collectors.toList());
        if (collectList==null || collectList.size()==0) {
            throw new CensusAnalyserException("No such Data Found", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        this.sort(collectList,this.sortMap.get(sortField));
        Collections.reverse(collectList);
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }
}