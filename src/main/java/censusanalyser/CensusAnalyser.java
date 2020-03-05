package censusanalyser;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    List<IndiaCensusDTO> censusListDto=null;
    Map<String,IndiaCensusDTO> censusMap=null;
    List<IndiaCensusDTO> collectList=null;

    public CensusAnalyser() {
        this.censusListDto=new ArrayList<>();
        this.censusMap=new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))){
            ICsvBuilder openCsvBuilder = CsvBuilderFactory.getOpenCsvBuilder();
            Iterator<IndiaCensusCSV>censusCSVIterator = openCsvBuilder.getCsvFileIterator(reader,IndiaCensusCSV.class);
            while(censusCSVIterator.hasNext())
            {
               // this.censusListDto.add(new IndiaCensusDTO(censusCSVIterator.next()));
                IndiaCensusCSV indianCensuscsv = censusCSVIterator.next();
                this.censusMap.put(indianCensuscsv.state,new IndiaCensusDTO(indianCensuscsv));
            }
            collectList = censusMap.values().stream().collect(Collectors.toList());
            return collectList.size();
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


            while (codeDataIterator.hasNext())
            {
                IndiaStateCodeData indiaStateCodeData=codeDataIterator.next();
                IndiaCensusDTO indiaCensusDTO = this.censusMap.get(indiaStateCodeData.stateName);
                if (indiaCensusDTO==null)
                {
                    continue;
                }
                indiaCensusDTO.state=indiaStateCodeData.stateName;
            }
            return censusMap.size();
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    public String getStateWiseSortedData() {
            if (collectList==null || collectList.size()==0)
            {
                throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
            }
            Comparator<IndiaCensusDTO> indiaCensusCSVComparator=Comparator.comparing(census ->census.state);
            this.sort(indiaCensusCSVComparator);
            String sortedStateCensus=new Gson().toJson(collectList);
            return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusDTO> indiaCensusDTOComparator) {
        for (int i=0;i<collectList.size()-1;i++)
        {
            for (int j=0;j<collectList.size()-i-1;j++)
            {
                IndiaCensusDTO census1= collectList.get(j);
                IndiaCensusDTO census2=collectList.get(j+1);
                if (indiaCensusDTOComparator.compare(census1,census2)>0) {
                    collectList.set(j, census2);
                    collectList.set(j + 1, census1);
                }
            }
        }
    }

    public String getStateWisePopulatedData() {
        if (collectList==null || collectList.size()==0)
        {
            throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> indiaCensusDTOComparator2=Comparator.comparing(census ->census.population);
        this.sort(indiaCensusDTOComparator2);
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }

    public String getStateWiseDenseData() {
        if (collectList==null || collectList.size()==0)
        {
            throw new CensusAnalyserException("No such Data Found",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDTO> indiaCensusDTOComparator1=Comparator.comparing(census ->census.densityPerSqKm);
        this.sort(indiaCensusDTOComparator1);
        String sortedStatePopulation=new Gson().toJson(collectList);
        return sortedStatePopulation;
    }
}
