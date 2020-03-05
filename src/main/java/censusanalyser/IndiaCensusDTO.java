package censusanalyser;

public class IndiaCensusDTO {
    public String state;
    public int population;
    public int areaInSqKm;
    public int densityPerSqKm;

    public IndiaCensusDTO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
    }
}
