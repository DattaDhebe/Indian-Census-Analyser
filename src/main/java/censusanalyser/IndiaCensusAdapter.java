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

public class IndiaCensusAdapter extends CensusAdapter {

    Map<String,CensusDAO> censusStateMap=new HashMap<>();
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusStateMap = super.loadCensusData(IndiaStateCSV.class, csvFilePath[0]);
        this.loadIndianStateCodeData(censusStateMap, csvFilePath[1]);
        return censusStateMap;
    }

    private Integer loadIndianStateCodeData(Map<String, CensusDAO> csvFileMap, String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader,IndiaStateCSV.class);
            Iterable<IndiaStateCSV> csvIterable = () -> csvFileIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .filter(csvState -> csvFileMap.get(csvState.state) != null)
                    .forEach(censusCSV -> csvFileMap.get(censusCSV.state).state = censusCSV.stateCode);
            return csvFileMap.size();
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .NO_FILE_FOUND, "File Not Found in Path");
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .INCORRECT_DELIMITER_HEADER_EXCEPTION, "Header or Delimiter is not proper");
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(),e.type.name());
        } catch (IOException e) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType
                                             .INCORRECT_DELIMITER_EXCEPTION,"File Not Proper");
        }
    }
}