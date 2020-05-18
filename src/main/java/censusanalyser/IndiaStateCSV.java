package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCSV {

    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "TIN", required = true)
    public int tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCSV{" +
                "srNo='" + srNo + '\'' +
                ", state='" + state + '\'' +
                ", tin=" + tin +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
