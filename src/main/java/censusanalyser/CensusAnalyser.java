package censusanalyser;

import com.builder.censusanalyser.CSVBuilderException;
import com.builder.censusanalyser.CSVBuilderFactory;
import com.builder.censusanalyser.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            CensusAnalyserException.ExceptionType type = null;
            if (e.getMessage().contains("CsvRequiredFieldEmptyException")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM;
            } else if (e.getMessage().contains("Error capturing CSV header!")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM;
            }
            throw new CensusAnalyserException(e.getMessage(), type);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = csvBuilder.getCSVFileIterator(reader,
                                                                                        IndiaStateCodeCSV.class);
            return this.getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            CensusAnalyserException.ExceptionType type = null;
            if (e.getMessage().contains("CsvRequiredFieldEmptyException")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM;
            } else if (e.getMessage().contains("Error capturing CSV header!")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM;
            }
            throw new CensusAnalyserException(e.getMessage(), type);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }


    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }
}
