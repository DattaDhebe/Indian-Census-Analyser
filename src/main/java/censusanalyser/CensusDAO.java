package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public double housingDensity;
    public double totalArea;


    public CensusDAO(CensusDAO indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCSV indiaStateCSV) {
        stateCode = indiaStateCSV.stateCode;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        stateCode = usCensusCSV.stateId;
        state = usCensusCSV.stateName;
        population = usCensusCSV.population;
        housingDensity = usCensusCSV.housingDensity;
        totalArea = usCensusCSV.totalArea;
    }

    public CensusDAO() {
    }
}
