package com.censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser<E> {

    Map<String, CensusDAO> csvFileMap;

    public enum Country{ INDIA, US }
    private Country country;

    public CensusAnalyser(Country country) {
        this.country=country;
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        csvFileMap=new CensusAdapterFactory().censusFactory(country,csvFilePath);
        return csvFileMap.size();
    }

    public String getStateWiseSortedCensusData() {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        return sort(censusComparator);
    }

    public String getStateCodeWiseSortedStateCodeData() {
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        return sort(censusComparator);
    }

    public String getPopulationWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadCensusData(csvFilePath);
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        return sort(censusComparator);
    }

    public String getDensityWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadCensusData(csvFilePath);
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        return sort(censusComparator);
    }

    public String getAreaWiseSortedCensusData(String csvFilePath) throws CensusAnalyserException {
        loadCensusData(csvFilePath);
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        return sort(censusComparator);
    }

    private String sort(Comparator<CensusDAO> censusComparator) {
        ArrayList censusDTOS = csvFileMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(Collectors.toCollection(ArrayList::new));
        return new Gson().toJson(censusDTOS);
    }
}