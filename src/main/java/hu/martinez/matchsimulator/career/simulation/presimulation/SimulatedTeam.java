package hu.martinez.matchsimulator.career.simulation.presimulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulatedTeam {

    private Integer id;
    private String name;
    private Integer matchesPlayed;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer goalsScored;
    private Integer goalsConceded;
    private Integer points;

}
