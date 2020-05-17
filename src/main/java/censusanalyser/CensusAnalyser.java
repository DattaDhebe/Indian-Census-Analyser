package censusanalyser;

import com.google.gson.Gson;
import java.util.*;

public class CensusAnalyser<E> {

    List<IndiaCensusCSV> censusList = null;
    List<IndiaStateCSV> codeCSVList=null;

    Map<String, CensusDAO> csvFileMap = null;

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException {
        csvFileMap= new CensusLoader().loadCensusData(IndiaCensusCSV.class,csvFilePath);
        return csvFileMap.size();
    }

    public int loadUSCensusData (String... csvFilePath) throws CensusAnalyserException {
        csvFileMap=new CensusLoader().loadCensusData(IndiaCensusCSV.class,csvFilePath);
        return csvFileMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList==null || censusList.size()==0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        if (codeCSVList==null || codeCSVList.size()==0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA,"NO Data Found");
        }
        Comparator<IndiaStateCSV> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
        this.sortStateCode(stateCodeComparator);
        String sortedStateCensusJson = new Gson().toJson(this.codeCSVList);
        return sortedStateCensusJson;
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.population);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException(CensusAnalyserException.ExceptionType.NO_CENSUS_DATA, "NO_CENSUS_DATA");
        }
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sortCensusData(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(this.censusList);
        return sortedStateCensusJson;
    }

    private void sortStateCode(Comparator<IndiaStateCSV> stateCodeComparator) {
        for (int i = 0; i < codeCSVList.size()-1; i++) {
            for(int j = 0; j < codeCSVList.size()-i-1; j++) {
                IndiaStateCSV census1 = codeCSVList.get(j);
                IndiaStateCSV census2= codeCSVList.get(j+1);
                if(stateCodeComparator.compare(census1,census2) > 0) {
                    codeCSVList.set(j, census2);
                    codeCSVList.set(j+1, census1);
                }
            }
        }
    }

    private void sortCensusData(Comparator<IndiaCensusCSV> censusComparator) {
        for (int i = 0; i < censusList.size()-1; i++) {
            for(int j = 0; j < censusList.size()-i-1; j++) {
                IndiaCensusCSV census1 = censusList.get(j);
                IndiaCensusCSV census2= censusList.get(j+1);
                if(censusComparator.compare(census1,census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j+1, census1);
                }
            }
        }
    }
}