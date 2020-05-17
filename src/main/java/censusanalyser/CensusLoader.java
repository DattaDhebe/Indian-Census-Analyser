
package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;
import static java.nio.file.Files.newBufferedReader;

public class CensusLoader {

    public <E> Map<String, CensusDAO> loadCensusData(Class<E> CensusCsvClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> csvFileMap = new HashMap<>();
        try (Reader reader = newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader,CensusCsvClass);
            Iterable<E> csvIterable = () -> csvFileIterator;
            if(CensusCsvClass.getName().equals("com.bridgelabz.CSVStateCensus")) {
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            } else if (CensusCsvClass.getName().equals("com.bridgelabz.CSVUSCensus")){
                StreamSupport.stream(csvIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> csvFileMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFilePath.length == 1) return csvFileMap;
            this.loadIndianStateCodeData(csvFileMap, csvFilePath[1]);
            return csvFileMap;
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_FILE_FOUND,"File Not Found in Path");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (IOException e){
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        }
    }

    public Integer loadIndianStateCodeData(Map<String, CensusDAO> csvFileMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCSV.class);
            Iterable<IndiaStateCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> csvFileMap.get(csvState.state) != null)
                    .forEach(censusCSV -> csvFileMap.get(censusCSV.state).state = censusCSV.stateCode);
            return csvFileMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_FILE_FOUND, "File Not Found in Path");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        }
    }
}