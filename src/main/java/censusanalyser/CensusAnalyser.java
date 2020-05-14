package censusanalyser;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCSV> stateCSVList = null;

    public CensusAnalyser() {
        this.stateCSVList = new ArrayList<IndiaStateCSV>();
        this.censusCSVList = new ArrayList<IndiaCensusCSV>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaStateCSV> stateCSVList = csvBuilder.getCSVFileList(reader, IndiaStateCSV.class);
            return stateCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);

        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.censusSortList(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusCSVList);
        return sortedStateCensusJson;
    }

    public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
        if (stateCSVList == null || stateCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Code Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);

        }
        Comparator<IndiaStateCSV> censusComparator = Comparator.comparing(census -> census.stateCode);
        this.stateSortList(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.stateCSVList);
        return sortedStateCensusJson;
    }

    private void censusSortList(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i=0; i<censusCSVList.size()-1; i++) {
            for (int j=0; j<censusCSVList.size()-i-1; j++) {
                IndiaCensusCSV census1 = censusCSVList.get(j);
                IndiaCensusCSV census2 = censusCSVList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void stateSortList(Comparator<IndiaStateCSV> censusComparator) {
        for (int i=0; i<stateCSVList.size()-1; i++) {
            for (int j=0; j<stateCSVList.size()-i-1; j++) {
                IndiaStateCSV census1 = stateCSVList.get(j);
                IndiaStateCSV census2 = stateCSVList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0) {
                    stateCSVList.set(j, census2);
                    stateCSVList.set(j+1, census1);
                }
            }
        }
    }


}