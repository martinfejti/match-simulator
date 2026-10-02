package hu.martinez.matchsimulator.career.simulation.postsimulation.exclusion;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedBenchedPlayer;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExclusionDecrementService {

    public void handleExclusionDecrement(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {
        handleExclusionDecrementForTeam(fixtureDataContainer.homeTeamDataContainer().benchedPlayerList());
        handleExclusionDecrementForTeam(fixtureDataContainer.awayTeamDataContainer().benchedPlayerList());
    }

    private void handleExclusionDecrementForTeam(@Nonnull List<SimulatedBenchedPlayer> benchedPlayerList) {
        benchedPlayerList
                .stream()
                .filter(player -> player.getExcludedFor() > 0)
                .forEach(this::handleExclusionDecrementForPlayer);
    }

    private void handleExclusionDecrementForPlayer(@Nonnull SimulatedBenchedPlayer benchedPlayer) {
        benchedPlayer.setExcludedFor(benchedPlayer.getExcludedFor() - 1);
    }

}
