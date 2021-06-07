package censusanalyser;

import com.builder.censusanalyser.CSVBuilderException;
import com.builder.censusanalyser.CSVBuilderFactory;
import com.builder.censusanalyser.ICSVBuilder;
import com.google.gson.Gson;


import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
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

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList.equals(null) || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensus = new Gson().toJson(censusCSVList);
        return sortedStateCensus;
    }

    private void sort(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusCSVList.size(); i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j + 1, census1);
                }
            }
        }
    }
}
