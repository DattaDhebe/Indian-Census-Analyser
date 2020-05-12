package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "State Name='" + state + '\'' +
                ", StateCode=" + stateCode +
                '}';
    }
}
