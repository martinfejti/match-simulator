package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedBenchedPlayer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class InjuryService {

    public void handleInjuries(@Nonnull PreSimulationTeamDataContainer teamDataContainer) {

        // starters
        teamDataContainer.forwardList().forEach(this::handleInjuryForStarterPlayer);
        teamDataContainer.midfielderList().forEach(this::handleInjuryForStarterPlayer);
        teamDataContainer.defenderList().forEach(this::handleInjuryForStarterPlayer);
        handleInjuryForStarterPlayer(teamDataContainer.goalkeeper());

        // bench
        handleInjuryForBenchedPlayers(teamDataContainer.benchedPlayerList());
    }

    private void handleInjuryForStarterPlayer(@Nonnull SimulatedStarterPlayer starterPlayer) {

        if (Math.random() <= 0.01) { // 1% chance that an injury happens

            var injuryLengthRandom = Math.random();
            if (injuryLengthRandom >= 0.9) { // 10% chance for 5 week injury
                starterPlayer.setInjuredFor(5);
            } else if (injuryLengthRandom >= 0.75) { // 15% chance for 4 week injury
                starterPlayer.setInjuredFor(4);
            } else if (injuryLengthRandom >= 0.5) { // 25% chance for 3 week injury
                starterPlayer.setInjuredFor(3);
            } else if (injuryLengthRandom >= 0.2) { // 30% chance for 2 week injury
                starterPlayer.setInjuredFor(2);
            } else { // 20% chance for 1 week injury
                starterPlayer.setInjuredFor(1);
            }
            log.debug("handleInjuryForStarterPlayer - {} week injury happened to {}!",
                    starterPlayer.getInjuredFor(), starterPlayer.getName());
        }
    }

    private void handleInjuryForBenchedPlayers(@Nonnull List<SimulatedBenchedPlayer> benchedPlayerList) {
        benchedPlayerList
                .stream()
                .filter(player -> player.getInjuredFor() > 0)
                .forEach(player -> player.setInjuredFor(player.getInjuredFor() - 1));
    }

}
