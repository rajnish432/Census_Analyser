package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH="/home/admin2/Desktop/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIA_STATE_CODE_FILE_PATH="./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_TYPE_INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

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
            int numOfRecords = censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFile_ShouldThrowException() {
        try {
            censusAnalyser.loadIndianStateCode(WRONG_INDIA_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongType_ShouldThrowException() {
        try {
            censusAnalyser.loadIndiaCensusData(WRONG_TYPE_INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_TYPE_PROBLEM,e.type);
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
