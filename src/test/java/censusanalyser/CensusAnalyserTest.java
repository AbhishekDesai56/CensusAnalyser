package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_EXTENSION_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WrongExtensionType_ShouldThrowException() {
        String getExtensionType = "";
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            getExtensionType = WRONG_EXTENSION_FILE_PATH.substring(WRONG_CSV_FILE_PATH.length() - 3);
            censusAnalyser.loadIndiaCensusData(WRONG_EXTENSION_FILE_PATH);

        } catch (CensusAnalyserException e) {
            Assert.assertEquals("csv", getExtensionType);
        }
    }

    @Test
    public void givenIndiaCensusData_WrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_CsvHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
        Assert.assertEquals(37,numOfStateCode);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianStateCSV_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_WrongExtensionType_ShouldThrowException() {
        String getExtensionType = "";
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            getExtensionType = WRONG_EXTENSION_FILE_PATH.substring(WRONG_CSV_FILE_PATH.length() - 3);
            censusAnalyser.loadIndianStateCode(WRONG_EXTENSION_FILE_PATH);

        } catch (CensusAnalyserException e) {
            Assert.assertEquals("csv", getExtensionType);
        }
    }

    @Test
    public void givenIndianStateCSV_WrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_CENSUS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_CsvHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertNotEquals(CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM, e.type);
        }
    }
}
