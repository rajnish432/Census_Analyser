package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH="/home/admin2/Desktop/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_FILE_PATH = "./src/test/resources/USCensusData.csv";

    CensusAnalyser censusAnalyser = new CensusAnalyser();

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianStateCode_ShouldReturnRecordCount() {
        try {
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_FILE_PATH);
            int loadIndiaCensusData = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,loadIndiaCensusData);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ReturnsSortedCensusData() {
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData=censusAnalyser.getStateWiseSortedData();
        IndiaCensusCSV[] indiaCensusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",indiaCensusCSV[0].state);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ReturnsLeastPopulousState() {
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedData = censusAnalyser.getStateWisePopulatedData();
        System.out.println(stateWiseSortedData);
        IndiaCensusCSV[] indiaCensusCSVS=new Gson().fromJson(stateWiseSortedData,IndiaCensusCSV[].class);
        Assert.assertEquals(607688,indiaCensusCSVS[0].population);
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ReturnsLeastDensityState() {
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String stateWiseSortedData=censusAnalyser.getStateWiseDenseData();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(stateWiseSortedData, IndiaCensusCSV[].class);
        Assert.assertEquals(50,indiaCensusCSVS[0].populationDensity);
    }

    @Test
    public void givenUsCensusCSVFile_ShouldReturnsCorrectRecords() {
        int totalRecords=censusAnalyser.loadUSCensusData(US_CENSUS_FILE_PATH);
        Assert.assertEquals(51,totalRecords);
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }

    }
}
