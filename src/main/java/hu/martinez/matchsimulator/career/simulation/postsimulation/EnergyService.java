package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedBenchedPlayer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Log4j2
@Service
public class EnergyService {

    public void handleFatigue(@Nonnull PreSimulationTeamDataContainer teamDataContainer) {

        // starters
        handleFatigueForStarterPlayers(teamDataContainer.forwardList());
        handleFatigueForStarterPlayers(teamDataContainer.midfielderList());
        handleFatigueForStarterPlayers(teamDataContainer.defenderList());
        handleFatigueForStarterPlayer(teamDataContainer.goalkeeper());

        // bench
        handleFatigueForBenchedPlayers(teamDataContainer.benchedPlayerList());
    }

    private void handleFatigueForStarterPlayers(@Nonnull List<SimulatedStarterPlayer> starterPlayerList) {
        starterPlayerList.forEach(this::handleFatigueForStarterPlayer);
    }

    private void handleFatigueForStarterPlayer(@Nonnull SimulatedStarterPlayer starterPlayer) {

        // TODO en case of goalkeeper, this should be the half of it!
        var energyLoss = new Random().nextInt(16) + 10; // random number between 10 and 25

        starterPlayer.setEnergy(starterPlayer.getEnergy() - energyLoss);

        log.debug("handleFatigueForStarterPlayer - {} energy level: {}",
                starterPlayer.getName(), starterPlayer.getEnergy());
    }

    private void handleFatigueForBenchedPlayers(@Nonnull List<SimulatedBenchedPlayer> benchedPlayerList) {
        benchedPlayerList.forEach(benchedPlayer -> benchedPlayer.setEnergy(100));
    }

}
