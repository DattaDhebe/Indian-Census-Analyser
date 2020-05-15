package censusanalyser;

public class IndiaCensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public int densityPerSqKm;
    public int areaInSqKm;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
    }

    public IndiaCensusDAO(IndiaStateCSV indiaStateCSV) {
        stateCode = indiaStateCSV.stateCode;
    }
}
