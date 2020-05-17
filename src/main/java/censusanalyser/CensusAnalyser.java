package censusanalyser;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


public class CensusAnalyser {

    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCSV> stateCSVList = null;
    List<USCensusCSV> usCensusCSVList = null;

    Map<String, IndiaCensusCSV> censusCSVMap = null;
    Map<String, IndiaStateCSV> stateCSVMap = null;
    Map<String , USCensusCSV> usCensusCSVMap = null;

    public CensusAnalyser() {
        this.stateCSVMap = new HashMap<>();
        this.censusCSVMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                IndiaCensusCSV indiaCensusCSV = csvFileIterator.next();
                this.censusCSVMap.put(indiaCensusCSV.state, indiaCensusCSV);
                censusCSVList = censusCSVMap.values().stream().collect(Collectors.toList());
            }
            return censusCSVMap.size();
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
            Iterator<IndiaStateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCSV.class);
            while (csvFileIterator.hasNext()) {
                IndiaStateCSV indiaStateCSV = csvFileIterator.next();
                this.stateCSVMap.put(indiaStateCSV.stateCode, indiaStateCSV);
                stateCSVList = stateCSVMap.values().stream().collect(Collectors.toList());
            }
            return stateCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader,USCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                USCensusCSV usCensusCSV = csvFileIterator.next();
                this.usCensusCSVMap.put(usCensusCSV.state,usCensusCSV);
                usCensusCSVList = usCensusCSVMap.values().stream().collect(Collectors.toList());
            }
            return usCensusCSVMap.size();
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

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
        this.censusSortList(censusComparator);
        String sortedPopulationCensusJson = new Gson().toJson(this.censusCSVList);
        return sortedPopulationCensusJson;
    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.censusSortList(censusComparator);
        String sortedDensityCensusJson = new Gson().toJson(this.censusCSVList);
        return sortedDensityCensusJson;
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.censusSortList(censusComparator);
        String sortedAreaCensusJson = new Gson().toJson(this.censusCSVList);
        return sortedAreaCensusJson;
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