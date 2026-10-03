package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.matchevent.MatchEventContainer;
import hu.martinez.matchsimulator.career.simulation.postsimulation.energy.EnergyService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.exclusion.ExclusionDecrementService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.fixturefinisher.FixtureFinisherService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.matchesplayed.MatchesPlayedService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.teamstatistics.TeamStatisticsService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostSimulationService {

    private final EnergyService energyService;
    private final ExclusionDecrementService exclusionDecrementService;
    private final FixtureFinisherService fixtureFinisherService;
    private final MatchesPlayedService matchesPlayedService;
    private final TeamStatisticsService teamStatisticsService;

    public void handlePostSimulationTasks(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull MatchEventContainer matchEventContainer
    ) {

        exclusionDecrementService.handleExclusionDecrement(fixtureDataContainer);
        energyService.handleFatigue(fixtureDataContainer);
        matchesPlayedService.handleMatchesPlayer(fixtureDataContainer);
        teamStatisticsService.handleTeamStatistics(fixtureDataContainer, matchEventContainer);
        fixtureFinisherService.finishFixture(fixtureDataContainer.simulatedFixture());
    }

}
