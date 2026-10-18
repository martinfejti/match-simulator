package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChanceSimulatorService2 {

    private final GoalSimulationService goalSimulationService;

    @Nonnull
    public GoalsContainer simulateChances(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull ChanceContainer chanceContainer
    ) {

        // home team
        List<PlayerAndChance> homeTeamPlayerAndChanceList = new ArrayList<>();

        for (var i = 0; i < chanceContainer.numberOfHomeSmallChances(); i++) {
            homeTeamPlayerAndChanceList.add(getPlayerToHaveSmallChance(fixtureDataContainer.homeTeamDataContainer()));
        }
        for (var i = 0; i < chanceContainer.numberOfHomeBigChances(); i++) {
            homeTeamPlayerAndChanceList.add(getPlayerToHaveBigChance(fixtureDataContainer.homeTeamDataContainer()));
        }

        // away team
        List<PlayerAndChance> awayTeamPlayerAndChanceList = new ArrayList<>();

        for (var i = 0; i < chanceContainer.numberOfAwaySmallChances(); i++) {
            awayTeamPlayerAndChanceList.add(getPlayerToHaveSmallChance(fixtureDataContainer.awayTeamDataContainer()));
        }
        for (var i = 0; i < chanceContainer.numberOfAwayBigChances(); i++) {
            awayTeamPlayerAndChanceList.add(getPlayerToHaveBigChance(fixtureDataContainer.awayTeamDataContainer()));
        }

        return goalSimulationService.simulateGoals(
                fixtureDataContainer,
                homeTeamPlayerAndChanceList,
                awayTeamPlayerAndChanceList
        );
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChance(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        PlayerAndChance playerAndChance;

        if ("4-3-3".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveSmallChanceIn433Formation(teamDataContainer);
        } else if ("4-4-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveSmallChanceIn442Formation(teamDataContainer);
        } else if ("4-2-3-1".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveSmallChanceIn4231Formation(teamDataContainer);
        } else if ("3-5-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveSmallChanceIn352Formation(teamDataContainer);
        } else if ("5-3-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveSmallChanceIn532Formation(teamDataContainer);
        } else {
            throw new IllegalStateException("Unknown formation!");
        }

        return playerAndChance;
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChance(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        PlayerAndChance playerAndChance;

        // big chances
        if ("4-3-3".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveBigChanceIn433Formation(teamDataContainer);
        } else if ("4-4-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveBigChanceIn442Formation(teamDataContainer);
        } else if ("4-2-3-1".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveBigChanceIn4231Formation(teamDataContainer);
        } else if ("3-5-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveBigChanceIn352Formation(teamDataContainer);
        } else if ("5-3-2".equals(teamDataContainer.formation())) {
            playerAndChance = getPlayerToHaveBigChanceIn532Formation(teamDataContainer);
        } else {
            throw new IllegalStateException("Unknown formation!");
        }

        return playerAndChance;
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChanceIn433Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.05) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.11) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.16) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.26) { // centre def mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CDM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.5) { // centre mids
            var centreMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreMidfielderList.get(ThreadLocalRandom.current().nextInt(centreMidfielderList.size()));
        } else if (randomValue <= 0.7) { // right winger
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "RW".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.9) { // left winger
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "LW".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }

        return new PlayerAndChance(playerToHaveChance, false);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChanceIn442Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.045) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.105) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.15) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.42) { // centre mids
            var centreMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreMidfielderList.get(ThreadLocalRandom.current().nextInt(centreMidfielderList.size()));
        } else if (randomValue <= 0.585) { // right mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.75) { // left mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // strikers
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, false);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChanceIn4231Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.045) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.105) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.15) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.31) { // centre def mid
            var centreDefensiveMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CDM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreDefensiveMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreDefensiveMidfielderList.size()));
        } else if (randomValue <= 0.46) { // centre attacking mids
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CAM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.61) { // right winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.76) { // left winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }

        return new PlayerAndChance(playerToHaveChance, false);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChanceIn352Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.06) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.32) { // centre mids
            var centreDefensiveMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreDefensiveMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreDefensiveMidfielderList.size()));
        } else if (randomValue <= 0.48) { // centre attacking mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CAM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.545) { // right winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.61) { // left winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker TODO maybe strikers here need a bit lower and wingers a bit more
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, false);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveSmallChanceIn532Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.075) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.14) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RWB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.205) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LWB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }  else if (randomValue <= 0.505) { // centre mids
            var centreDefensiveMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreDefensiveMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreDefensiveMidfielderList.size()));
        } else { // striker
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, false);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChanceIn433Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.015) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.115) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.13) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.14) { // centre def mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CDM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.34) { // centre mids
            var centreMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreMidfielderList.get(ThreadLocalRandom.current().nextInt(centreMidfielderList.size()));
        } else if (randomValue <= 0.52) { // right winger
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "RW".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.70) { // left winger
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "LW".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }

        return new PlayerAndChance(playerToHaveChance, true);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChanceIn442Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.055) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.085) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.14) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.30) { // centre mids
            var centreMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreMidfielderList.get(ThreadLocalRandom.current().nextInt(centreMidfielderList.size()));
        } else if (randomValue <= 0.4) { // right mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.5) { // left mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // strikers
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, true);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChanceIn4231Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.015) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.125) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.14) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.17) { // centre def mid
            var centreDefensiveMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CDM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreDefensiveMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreDefensiveMidfielderList.size()));
        } else if (randomValue <= 0.37) { // centre attacking mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CAM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.52) { // right winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.67) { // left winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker
            playerToHaveChance = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }

        return new PlayerAndChance(playerToHaveChance, true);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChanceIn352Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.12) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.22) { // centre mids
            var centreMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreMidfielderList.size()));
        } else if (randomValue <= 0.335) { // centre attacking mid
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CAM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.425) { // right winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "RM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.515) { // left winger
            playerToHaveChance = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "LM".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else { // striker
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, true);
    }

    @Nonnull
    private PlayerAndChance getPlayerToHaveBigChanceIn532Formation(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer
    ) {

        SimulatedStarterPlayer playerToHaveChance;

        var randomValue = Math.random();
        if (randomValue <= 0.21) { // centre backs
            var centreBackList = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "CB".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreBackList.get(ThreadLocalRandom.current().nextInt(centreBackList.size()));
        } else if (randomValue <= 0.225) { // right back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "RWB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        } else if (randomValue <= 0.235) { // left back
            playerToHaveChance = teamDataContainer.defenderList()
                    .stream()
                    .filter(player -> "LWB".equals(player.getPosition()))
                    .findFirst()
                    .orElseThrow();
        }  else if (randomValue <= 0.34) { // centre mids
            var centreDefensiveMidfielderList = teamDataContainer.midfielderList()
                    .stream()
                    .filter(player -> "CM".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = centreDefensiveMidfielderList.get(
                    ThreadLocalRandom.current().nextInt(centreDefensiveMidfielderList.size()));
        } else { // striker
            var strikerList = teamDataContainer.forwardList()
                    .stream()
                    .filter(player -> "ST".equals(player.getPosition()))
                    .toList();
            playerToHaveChance = strikerList.get(ThreadLocalRandom.current().nextInt(strikerList.size()));
        }

        return new PlayerAndChance(playerToHaveChance, true);
    }

}
