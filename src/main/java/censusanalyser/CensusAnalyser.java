package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndiaCensusCSV.class);
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
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            Iterator<IndiaStateCodeCSV> stateCSVIterator = this.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            return this.getCount(stateCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }  catch (RuntimeException e) {
            CensusAnalyserException.ExceptionType type = null;
            if (e.getMessage().contains("CsvRequiredFieldEmptyException")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_DELIMITER_PROBLEM;
            } else if (e.getMessage().contains("Error capturing CSV header!")) {
                type = CensusAnalyserException.ExceptionType.CENSUS_HEADER_PROBLEM;
            }
            throw new CensusAnalyserException(e.getMessage(), type);
        }
    }

    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException {
        try {
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<E> csvToBean = csvToBeanBuilder.build();
            return csvToBean.iterator();
        }
        catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        return (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
    }
}
