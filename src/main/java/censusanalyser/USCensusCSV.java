package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State_Id", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String stateName;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Housing_units", required = true)
    public double housingUnits;

    @CsvBindByName(column = "Total_area", required = true)
    public double totalArea;

    @CsvBindByName(column = "Water_area", required = true)
    public double waterArea;

    @CsvBindByName(column = "Land_area", required = true)
    public double landArea;

    @CsvBindByName(column = "Population_Density", required = true)
    public double populationDensity;

    @CsvBindByName(column = "Housing_Density", required = true)
    public double housingDensity;

    @Override
    public String toString() {
        return "USCensusCSV{" +
                "stateId='" + stateId + '\'' +
                ", stateName='" + stateName + '\'' +
                ", population=" + population +
                ", housingUnits=" + housingUnits +
                ", totalArea=" + totalArea +
                ", waterArea=" + waterArea +
                ", landArea=" + landArea +
                ", populationDensity=" + populationDensity +
                ", housingDensity=" + housingDensity +
                '}';
    }
}
