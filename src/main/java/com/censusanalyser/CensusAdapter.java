package com.censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.nio.file.Files.newBufferedReader;

public abstract class CensusAdapter {

    Map<String,CensusDAO> csvFileMap = new HashMap<>();

    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;

    public <E> Map<String,CensusDAO> loadCensusData(Class<E> CensusCsvClass, String csvFilePath)
                                                    throws CensusAnalyserException {
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader,CensusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if(CensusCsvClass.getName().equals("com.censusanalyser.CSVStateCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (CensusCsvClass.getName().equals("com.censusanalyser.CSVUSCensus")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            return csvFileMap;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .NO_FILE_FOUND,"File Not Found in Path");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (IOException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }
}
