package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchResultHandlerService {

    public void handleMatchResults(
            @Nonnull PreSimulationTeamDataContainer homeTeamDataContainer,
            @Nonnull PreSimulationTeamDataContainer awayTeamDataContainer,
            @Nonnull Integer numberOfHomeGoals,
            @Nonnull Integer numberOfAwayGoals
    ) {
        // TODO we need the team attributes to change!!!
    }



}
