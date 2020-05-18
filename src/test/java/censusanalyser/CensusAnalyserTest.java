package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyserTest {

    CensusAnalyser censusAnalyserIndia =new CensusAnalyser(CensusAnalyser.Country.INDIA);
    CensusAnalyser censusAnalyserUS =new CensusAnalyser(CensusAnalyser.Country.US);

    public static String CSV_CENSUS_FILE_PATH = "./src/test/resources/StateCensusData.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE = "./src/test/resources/StateCensus.csv";
    public static String CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION = "./src/test/resources/StateCensus.jpg";
    public static String CSV_STATES_CODE_FILE_PATH = "./src/test/resources/StateCode.csv";
    public static String CSV_WRONG_STATES_CODE_FILE_PATH = "./src/test/resources/WrongStateCode.csv";
    public static String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenStateCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() {
        try {
            int numberOfRecords = censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileName_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperDelimiter_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCensusDataFile_WhenImproperHeader_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenTrue_ReturnNumberOfRecordMatch() {
        try {
            int numberOfRecords= censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            Assert.assertEquals(29, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenStateCode_WhenFalse_ReturnExceptionFileNotFound() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE,CSV_FILE_PATH_FOR_WRONG_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCodeFile_WhenImproperFileExtension_ReturnsException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION,CSV_FILE_PATH_FOR_WRONG_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.NO_FILE_FOUND,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperDelimiter_Should_ReturnException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenStateCode_WhenImproperHeader_Should_ReturnException() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);;
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedCensusData = censusAnalyserIndia.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnFirstValue_SortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyserIndia.getStateCodeWiseSortedStateCodeData();
            IndiaStateCSV[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,IndiaStateCSV[].class);
            Assert.assertEquals("AD",codeCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnLastValue_SortedResult() {
        try {
            censusAnalyserIndia.loadCensusData(CSV_CENSUS_FILE_PATH,CSV_STATES_CODE_FILE_PATH);
            String sortedStateCodeDataData = censusAnalyserIndia.getStateCodeWiseSortedStateCodeData();
            IndiaStateCSV[] codeCSV = new Gson().fromJson(sortedStateCodeDataData,IndiaStateCSV[].class);
            Assert.assertEquals("WB",codeCSV[36].stateCode);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturnSortedResult() {
        try {
            String sortedCensusData = censusAnalyserIndia.getPopulationWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(199812341,censusCSV[censusCSV.length - 1].population);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnPopulation_ShouldReturn_NumberOfStatesSortedResult() {
        try {
            String sortedCensusData = censusAnalyserIndia.getPopulationWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals(30,censusCSV.length+1);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_MostPopulationDensityState() {
        try {
            String sortedCensusData = censusAnalyserIndia.getDensityWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar",censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_LeastPopulationDensityState() {
        try {
            String sortedCensusData = censusAnalyserIndia.getDensityWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Arunachal Pradesh",censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_LargestStateByArea() {
        try {
            String sortedCensusData = censusAnalyserIndia.getAreaWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length - 1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturn_SmallestStateByArea() {
        try {
            String sortedCensusData = censusAnalyserIndia.getAreaWiseSortedCensusData(CSV_CENSUS_FILE_PATH);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Goa",censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusCSV_WhenConditionTrue_ReturnNumberOfRecordMatch() {
        try {
            int numberOfRecords = censusAnalyserUS.loadCensusData(US_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(51, numberOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }
}