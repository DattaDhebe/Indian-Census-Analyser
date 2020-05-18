package censusanalyser;

public class CensusDAO {

    public String stateCode;
    public double populationDensity;
    public double totalArea;
    public double population;
    public int densityPerSqKm;
    public int areaInSqKm;
    public String state;

    public CensusDAO(IndiaCensusCSV indiaCensus) {
        state=indiaCensus.state;
        areaInSqKm=indiaCensus.areaInSqKm;
        densityPerSqKm=indiaCensus.densityPerSqKm;
        population= indiaCensus.population;
    }

    public CensusDAO(USCensusCSV USCensus) {
        state = USCensus.state;
        population = USCensus.population;
        totalArea = USCensus.totalArea;
        populationDensity = USCensus.populationDensity;
        stateCode = USCensus.stateId;
    }

    public IndiaCensusCSV getCSVStateCensus() {
        return new IndiaCensusCSV(state, (int) population, (int) populationDensity, (int) totalArea);
    }

    public Object getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.US))
            return new IndiaCensusCSV(state, stateCode, population, populationDensity, totalArea);
        return new IndiaCensusCSV(state, (int) population, (int) populationDensity, (int) totalArea);
    }

}