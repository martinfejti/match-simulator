package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RedCardService {

    // TODO collect real life data
    public void handleRedCards(@Nonnull PreSimulationTeamDataContainer teamDataContainer) {

        teamDataContainer.forwardList().forEach(player -> handleRedCardForPlayer(player, 0.0025));
        teamDataContainer.midfielderList().forEach(player -> handleRedCardForPlayer(player, 0.005));
        teamDataContainer.defenderList().forEach(player -> handleRedCardForPlayer(player, 0.01));
        handleRedCardForPlayer(teamDataContainer.goalkeeper(), 0.001);
    }

    private void handleRedCardForPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull Double chanceOfGettingRedCard
    ) {

        if (Math.random() < chanceOfGettingRedCard) {
            starterPlayer.setNumberOfRedCards(starterPlayer.getNumberOfRedCards());

            var exclusionLengthValue = Math.random();
            var exclusionLength = 0;
            if (exclusionLengthValue <= 0.55) {
                exclusionLength = 1;
            } else if (exclusionLengthValue <= 0.8) {
                exclusionLength = 2;
            } else {
                exclusionLength = 3;
            }

            starterPlayer.setExcludedFor(exclusionLength);

            log.debug("handleRedCardForPlayer - RED for {} for {} matches",
                    starterPlayer.getName(), starterPlayer.getExcludedFor());
        }
    }

}
