package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
public class ChanceSimulatorService {

    // TODO first impressions
    // defenders should defo have more chances, they score barely
    // formation should have an impact on the number of chances (a sole striker scores crazy amount of goals) and the ratio of chance / position and even chance / position in position (CAM should have more chances than a CDM)
    // i think there are a bit too much goals, but we should see simulation with weaker teams also
    // i should check real life data to have a more realistic aim here: pros and cons of formations, positions with higher chance possibility etc (this also should be the case for cards for example!)
    // overall feeling: maybe there should be more chances but fewer goals - but i think first i need more test result with diffrent teams to decide it!
    // maybe low energy also should have an impact on scoring or getting into chances...

    // TODO this is a bit different, but i think we defo should have a system for downgrading overall if a player plays outside of his natural position!
    // this could be handled in the pre simulation part, where i map the overall into the simulation record

    public void simulateChances(
            @Nonnull PreSimulationTeamDataContainer homeTeamDataContainer,
            @Nonnull PreSimulationTeamDataContainer awayTeamDataContainer,
            @Nonnull ChanceContainer chanceContainer
    ) {

        // home team
        // big chances
        for (var i = 0; i < chanceContainer.numberOfHomeBigChances(); i++) {

            SimulatedStarterPlayer playerToHaveChance;
            var randomValue = Math.random();
            if (randomValue <= 0.6) {
                playerToHaveChance = homeTeamDataContainer.forwardList().get(
                        new Random().nextInt(homeTeamDataContainer.forwardList().size()));
            } else if (randomValue <= 0.9) {
                playerToHaveChance = homeTeamDataContainer.midfielderList().get(
                        new Random().nextInt(homeTeamDataContainer.midfielderList().size()));
            } else {
                playerToHaveChance = homeTeamDataContainer.defenderList().get(
                        new Random().nextInt(homeTeamDataContainer.defenderList().size()));
            }
            simulateChance(playerToHaveChance, true, awayTeamDataContainer.goalKeeperSaveBonus());
        }
        // small chances
        for (var i = 0; i < chanceContainer.numberOfHomeSmallChances(); i++) {

            SimulatedStarterPlayer playerToHaveChance;
            var randomValue = Math.random();
            if (randomValue <= 0.6) {
                playerToHaveChance = homeTeamDataContainer.forwardList().get(
                        new Random().nextInt(homeTeamDataContainer.forwardList().size()));
            } else if (randomValue <= 0.9) {
                playerToHaveChance = homeTeamDataContainer.midfielderList().get(
                        new Random().nextInt(homeTeamDataContainer.midfielderList().size()));
            } else {
                playerToHaveChance = homeTeamDataContainer.defenderList().get(
                        new Random().nextInt(homeTeamDataContainer.defenderList().size()));
            }
            simulateChance(playerToHaveChance, false, awayTeamDataContainer.goalKeeperSaveBonus());
        }

        // away team
        // home chances
        for (var i = 0; i < chanceContainer.numberOfAwayBigChances(); i++) {

            SimulatedStarterPlayer playerToHaveChance;
            var randomValue = Math.random();
            if (randomValue <= 0.6) {
                playerToHaveChance = awayTeamDataContainer.forwardList().get(
                        new Random().nextInt(awayTeamDataContainer.forwardList().size()));
            } else if (randomValue <= 0.9) {
                playerToHaveChance = awayTeamDataContainer.midfielderList().get(
                        new Random().nextInt(awayTeamDataContainer.midfielderList().size()));
            } else {
                playerToHaveChance = awayTeamDataContainer.defenderList().get(
                        new Random().nextInt(awayTeamDataContainer.defenderList().size()));
            }
            simulateChance(playerToHaveChance, true, awayTeamDataContainer.goalKeeperSaveBonus());
        }

        // small chances
        for (var i = 0; i < chanceContainer.numberOfAwaySmallChances(); i++) {

            SimulatedStarterPlayer playerToHaveChance;
            var randomValue = Math.random();
            if (randomValue <= 0.6) {
                playerToHaveChance = awayTeamDataContainer.forwardList().get(
                        new Random().nextInt(awayTeamDataContainer.forwardList().size()));
            } else if (randomValue <= 0.9) {
                playerToHaveChance = awayTeamDataContainer.midfielderList().get(
                        new Random().nextInt(awayTeamDataContainer.midfielderList().size()));
            } else {
                playerToHaveChance = awayTeamDataContainer.defenderList().get(
                        new Random().nextInt(awayTeamDataContainer.defenderList().size()));
            }
            simulateChance(playerToHaveChance, false, awayTeamDataContainer.goalKeeperSaveBonus());
        }
    }

    @Nonnull
    public void simulateChance(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Boolean isBigChance,
            @Nonnull Double goalKeeperSavingBonus
    ) {

        var forwardAdhocBonus = Math.random() / 10;
        var goalKeeperAdhocBonus = Math.random() / 10;

        if (isBigChance) {
            if (isGoalFromBigChance(player, forwardAdhocBonus, goalKeeperAdhocBonus, goalKeeperSavingBonus)) {
                log.info("simulatChance - GOAL for {} from a big chance", player.getName());
            }
        } else {
            if (isGoalFromSmallChance(player, forwardAdhocBonus, goalKeeperAdhocBonus, goalKeeperSavingBonus)) {
                log.info("simulatChance - GOAL for {} from a small chance", player.getName());
            }
        }

    }

    @Nonnull
    public Boolean isGoalFromBigChance(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double forwardAdhocBonus,
            @Nonnull Double goalKeeperAdhocBonus,
            @Nonnull Double goalKeeperSavingBonus
    ) {
        return Math.random() <=
                player.getBigChanceFinishing()
                + (player.getOverall() / 1000)
                + forwardAdhocBonus
                - goalKeeperAdhocBonus
                - goalKeeperSavingBonus;
    }

    @Nonnull
    public Boolean isGoalFromSmallChance(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double forwardAdhocBonus,
            @Nonnull Double goalKeeperAdhocBonus,
            @Nonnull Double goalKeeperSavingBonus
    ) {
        return Math.random() <=
                player.getSmallChanceFinishing()
                + (player.getOverall() / 1000)
                + forwardAdhocBonus
                - goalKeeperAdhocBonus
                - (1.5 * goalKeeperSavingBonus);
    }

}
