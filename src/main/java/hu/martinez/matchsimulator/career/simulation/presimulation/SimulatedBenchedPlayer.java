package hu.martinez.matchsimulator.career.simulation.presimulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulatedBenchedPlayer {

    private Integer id;
    private String name;
    private Integer energy;
    private Integer injuredFor;
    private Integer excludedFor;

}
