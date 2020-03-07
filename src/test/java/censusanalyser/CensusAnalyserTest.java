package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH="./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_FILE_PATH = "./src/test/resources/USCensusData.csv";

    CensusAnalyser censusAnalyser = new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int loadIndiaCensusData = censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(29,loadIndiaCensusData);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ReturnsSortedCensusData() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
        String sortedCensusData=censusAnalyser.getSortedData(SortField.STATE);
        IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",indiaCensusCSV[0].state);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ReturnsLeastDensityState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
        String stateWiseSortedData=censusAnalyser.getSortedData(SortField.POPULATIONDENSITY);
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(stateWiseSortedData, IndiaCensusCSV[].class);
        Assert.assertEquals(50,indiaCensusCSVS[0].populationDensity);
    }

    @Test
    public void givenUsCensusCSVFile_ShouldReturnsCorrectRecords() {
        int totalRecords=censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
        Assert.assertEquals(51,totalRecords);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulation_ReturnsMostPopulousState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
        String mostPopulusState=censusAnalyser.getReversedSortedData(SortField.POPULATION);
        USCensusData[] usCensusData=new Gson().fromJson(mostPopulusState,USCensusData[].class);
        Assert.assertEquals("California",usCensusData[0].state);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnPopulationDensity_ReturnsMostPopulationDenistyState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
        String mostDensityState=censusAnalyser.getReversedSortedData(SortField.POPULATIONDENSITY);
        USCensusData[] usCensusData=new Gson().fromJson(mostDensityState,USCensusData[].class);
        Assert.assertEquals("District of Columbia",usCensusData[0].state);
    }

    @Test
    public void givenUSCensusData_WhenSortedOnArea_ReturnsMaximumAreaState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
        String maxAreaState=censusAnalyser.getReversedSortedData(SortField.TOTALAREA);
        USCensusData[] usCensusData=new Gson().fromJson(maxAreaState,USCensusData[].class);
        Assert.assertEquals("Alaska",usCensusData[0].state);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ReturnsMostPopulousState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
        String mostPopulous=censusAnalyser.getReversedSortedData(SortField.POPULATION);
        IndiaCensusCSV[] indiaCensusCSVS=new Gson().fromJson(mostPopulous,IndiaCensusCSV[].class);
        Assert.assertEquals(199812341,indiaCensusCSVS[0].population);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnArea_ReturnsMaxAreaState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
        String maxAreaState=censusAnalyser.getReversedSortedData(SortField.TOTALAREA);
        IndiaCensusCSV[] indiaCensusCSVS=new Gson().fromJson(maxAreaState,IndiaCensusCSV[].class);
        Assert.assertEquals(342239,indiaCensusCSVS[0].totalArea);
    }

    @Test
    public void givenUSCensusDataAndIndiaCensusData_WhenSortedOnPopulation_ReturnsMaximumAreaState() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US, US_CENSUS_FILE_PATH);
        String mostPopulousStateUS= censusAnalyser.getSortedData(SortField.POPULATION);
        USCensusData[] usCensusData=new Gson().fromJson(mostPopulousStateUS,USCensusData[].class);
        CensusAnalyser censusAnalyser1=new CensusAnalyser();
        censusAnalyser1.loadCensusDatas(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_FILE_PATH);
        String mostPopulousStateIndia =censusAnalyser1.getSortedData(SortField.POPULATION);
        IndiaCensusCSV[] indiaCensusCSVS=new Gson().fromJson(mostPopulousStateIndia,IndiaCensusCSV[].class);
        Assert.assertEquals(true,(String.valueOf(usCensusData[0].population).compareToIgnoreCase(String.valueOf(indiaCensusCSVS[0].population))<0));
    }

    @Test
    public void getIndiaStateCodeData_WhenSorted_ReturnsStateCode() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_FILE_PATH);
        String stateCode=censusAnalyser.getSortedData(SortField.STATECODE);
        IndiaStateCodeData[] indiaStateCodeData=new Gson().fromJson(stateCode,IndiaStateCodeData[].class);
        Assert.assertEquals("AP",indiaStateCodeData[0].stateCode);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadCensusDatas(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
            } catch (CensusAnalyserException e) {
                Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);            }
    }

    @Test
    public void getUSCensusData_WhenSorted_ReturnsStateCode() {
        censusAnalyser.loadCensusDatas(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
        String stateCode=censusAnalyser.getSortedData(  SortField.STATEID);
        USCensusData[] usCensusData=new Gson().fromJson(stateCode,USCensusData[].class);
        Assert.assertEquals("AK",usCensusData[0].stateID);
    }
}

