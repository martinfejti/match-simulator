package hu.martinez.matchsimulator.career.simulation.presimulation;

public record PreSimulationFixtureDataContainer(
        SimulatedFixture simulatedFixture,
        PreSimulationTeamDataContainer homeTeamDataContainer,
        PreSimulationTeamDataContainer awayTeamDataContainer
) {
}
