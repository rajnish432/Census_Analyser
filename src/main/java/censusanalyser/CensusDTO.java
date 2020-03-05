package censusanalyser;

public class CensusDTO {
    public String state;
    public String stateID;
    public int population;
    public double totalArea;
    public double populationDensity;

    public CensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        totalArea = indiaCensusCSV.totalArea;
        populationDensity = indiaCensusCSV.populationDensity;
    }

    public CensusDTO(USCensusData usCensusData)
    {
        state=usCensusData.state;
        stateID=usCensusData.stateID;
        population=usCensusData.population;
        totalArea=usCensusData.totalArea;
        populationDensity=usCensusData.populationDensity;
    }
}
