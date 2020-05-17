package censusanalyser;

public class CensusDAO {

    public String state;
    public String stateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public double totalArea;
    public double populationDensity;

    public CensusDAO(CensusDAO indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public CensusDAO(IndiaStateCSV indiaStateCSV) {
        state = indiaStateCSV.state;
        stateCode = indiaStateCSV.stateCode;
    }
    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        population = usCensusCSV.population;
        totalArea = usCensusCSV.totalArea;
        populationDensity = usCensusCSV.populationDensity;
        stateCode = usCensusCSV.stateId;
    }

    public CensusDAO() {
    }
}
