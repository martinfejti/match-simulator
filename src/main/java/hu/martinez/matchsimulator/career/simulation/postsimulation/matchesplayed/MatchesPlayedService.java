package hu.martinez.matchsimulator.career.simulation.postsimulation.matchesplayed;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class MatchesPlayedService {

    public void handleMatchesPlayer(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        fixtureDataContainer.homeTeamDataContainer().forwardList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        fixtureDataContainer.homeTeamDataContainer().midfielderList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        fixtureDataContainer.homeTeamDataContainer().defenderList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        increaseNumberOfMatchesPlayer(fixtureDataContainer.homeTeamDataContainer().goalkeeper());

        fixtureDataContainer.awayTeamDataContainer().forwardList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        fixtureDataContainer.awayTeamDataContainer().midfielderList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        fixtureDataContainer.awayTeamDataContainer().defenderList()
                .forEach(this::increaseNumberOfMatchesPlayer);
        increaseNumberOfMatchesPlayer(fixtureDataContainer.awayTeamDataContainer().goalkeeper());
    }

    private void increaseNumberOfMatchesPlayer(@Nonnull SimulatedStarterPlayer starterPlayer) {
        starterPlayer.setMatchesPlayed(starterPlayer.getMatchesPlayed() + 1);
    }

}
