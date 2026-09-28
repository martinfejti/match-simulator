package hu.martinez.matchsimulator.career.simulation.presimulation;

import java.util.List;

public record PreSimulationTeamDataContainer(
        String formation,
        List<SimulatedStarterPlayer> forwardList,
        List<SimulatedStarterPlayer> midfielderList,
        List<SimulatedStarterPlayer> defenderList,
        SimulatedStarterPlayer goalkeeper,
        List<SimulatedBenchedPlayer> benchedPlayerList,
        Double teamAverage,
        Double goalKeeperSaveBonus
) {
}
