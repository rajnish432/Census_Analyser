package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeData {
    @CsvBindByName(column ="SrNo",required = true)
    private String srno;
    @CsvBindByName(column = "StateName",required = true)
    private String stateName;
    @CsvBindByName(column = "TIN",required = true)
    private String tin;
    @CsvBindByName(column = "StateCode",required = true)
    private String stateCode;
}

