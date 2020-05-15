package censusanalyser;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class CensusAnalyser {

    List<CensusDAO> csvFileList;

    List<IndiaCensusCSV> censusCSVList = null;
    List<IndiaStateCSV> stateCSVList = null;

    Map<String, CensusDAO> censusCSVMap = null;

    public CensusAnalyser() {
        this.csvFileList = new ArrayList<CensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CensusDAO> csvIterator = csvBuilder.getCSVFileIterator(reader, CensusDAO.class);
            while (csvIterator.hasNext()) {
                this.csvFileList.add(new CensusDAO(csvIterator.next()));
            }
            return csvFileList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sortList(censusComparator);
        return new Gson().toJson(this.censusCSVList);
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sortList(censusComparator);
        return new Gson().toJson(this.censusCSVList);
    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sortList(censusComparator);
        return new Gson().toJson(this.censusCSVList);
    }

    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sortList(censusComparator);
        return new Gson().toJson(this.censusCSVList);
    }

    public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
        if (stateCSVList == null || stateCSVList.size() == 0) {
            throw new CensusAnalyserException("No State Code Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        this.sortList(censusComparator);
        return new Gson().toJson(this.stateCSVList);
    }

    private void sortList(Comparator<CensusDAO> censusComparator) {
        for (int i=0; i<csvFileList.size()-1; i++) {
            for (int j=0; j<csvFileList.size()-i-1; j++) {
                CensusDAO census1 = csvFileList.get(j);
                CensusDAO census2 = csvFileList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j+1, census1);
                }
            }
        }
    }

}