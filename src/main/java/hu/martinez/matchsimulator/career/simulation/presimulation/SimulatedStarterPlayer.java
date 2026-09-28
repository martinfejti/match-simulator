package hu.martinez.matchsimulator.career.simulation.presimulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulatedStarterPlayer {

    private Integer id;
    private String name;
    private String position;
    private Integer overall;
    private Double bigChanceFinishing;
    private Double smallChanceFinishing;
    private Integer energy;
    private Integer injuredFor;
    private Integer excludedFor;
    private Integer matchesPlayed;
    private Integer numberOfGoals;
    private Integer cleanSheets;
    private Integer numberOfYellowCards;
    private Integer numberOfRedCards;

}
