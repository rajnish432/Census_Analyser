package censusanalyser;

public class CensusDTO {
    public String state;
    public int population;
    public String stateID;
    public double totalArea;
    public double populationDensity;
    public String stateCode;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.totalArea;
        populationDensity = indiaCensusCSV.populationDensity;
    }

    public CensusDTO(USCensusData usCensusData)
    {
        state=usCensusData.state;
        stateCode=usCensusData.stateID;
        population=usCensusData.population;
        totalArea=usCensusData.totalArea;
        populationDensity=usCensusData.populationDensity;
        stateID=usCensusData.stateID;
    }
}
