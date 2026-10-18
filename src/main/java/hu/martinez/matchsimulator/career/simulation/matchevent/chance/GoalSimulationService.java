package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import hu.martinez.matchsimulator.career.goal.CreateGoal;
import hu.martinez.matchsimulator.career.simulation.matchevent.PositionCheckerService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class GoalSimulationService {

    private final PositionCheckerService positionCheckerService;

    @Nonnull
    public GoalsContainer simulateGoals(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull List<PlayerAndChance> homePlayerAndChanceList,
            @Nonnull List<PlayerAndChance> awayPlayerAndChanceList
    ) {

        // home
        List<Optional<CreateGoal>> homeGoalList = new ArrayList<>();

        homePlayerAndChanceList.forEach(
                playerAndChance -> homeGoalList.add(
                        simulateChance(
                                playerAndChance,
                                fixtureDataContainer.simulatedFixture().getId(),
                                fixtureDataContainer.awayTeamDataContainer().goalKeeperSaveBonus()
                        )
                )
        );

        // away
        List<Optional<CreateGoal>> awayGoalList = new ArrayList<>();

        awayPlayerAndChanceList.forEach(
                playerAndChance -> awayGoalList.add(
                        simulateChance(
                                playerAndChance,
                                fixtureDataContainer.simulatedFixture().getId(),
                                fixtureDataContainer.homeTeamDataContainer().goalKeeperSaveBonus()
                        )
                )
        );

        return new GoalsContainer(
                homeGoalList
                        .stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList(),
                awayGoalList
                        .stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
    }

    @Nonnull
    private Optional<CreateGoal> simulateChance(
            @Nonnull PlayerAndChance playerAndChance,
            @Nonnull Integer fixtureId,
            @Nonnull Double goalKeeperSavingBonus
    ) {

        if (playerAndChance.isBigChance()) {
            if (isGoalFromBigChance(playerAndChance.player(), goalKeeperSavingBonus)) {

                log.debug("simulateChance - GOAL from big chance: {}", playerAndChance.player().getName());

                playerAndChance.player().setNumberOfGoals(playerAndChance.player().getNumberOfGoals() + 1);

                return Optional.of(
                        new CreateGoal(
                                fixtureId,
                                playerAndChance.player().getTeamId(),
                                playerAndChance.player().getId()
                        )
                );
            }
        } else {
            if (isGoalFromSmallChance(playerAndChance.player(), goalKeeperSavingBonus)) {

                log.debug("simulateChance - GOAL from small chance: {}", playerAndChance.player().getName());

                playerAndChance.player().setNumberOfGoals(playerAndChance.player().getNumberOfGoals() + 1);

                return Optional.of(
                        new CreateGoal(
                                fixtureId,
                                playerAndChance.player().getTeamId(),
                                playerAndChance.player().getId()
                        )
                );
            }
        }

        return Optional.empty();
    }

    @Nonnull
    private Boolean isGoalFromBigChance(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double goalkeeperSavingBonus
    ) {

        // TODO small chances now seem quite OK in the short tests
        // big chances can be further nerfed a bit if i manage to create a bigger gap between them (like 5-0, 6-1, 4-1)

        // var bigChanceValue = player.getBigChanceFinishing() * 0.45;
        var bigChanceValue = player.getBigChanceFinishing() * 0.8;
        var fatigueValue = 1.0 - ((100 - player.getEnergy()) * 0.00375);
        var positionPenalty = getPossibleOutOfPositionPenalty(player);
        var optimizedGoalkeeperSavingValue = 1.0 - (goalkeeperSavingBonus / 2.5);
        var attackerAdhocBonus = 1.0 + (Math.random() / 20);
        var goalkeeperAdhocBonus = 1.0 - (Math.random() / 20);

        var result = bigChanceValue
                * fatigueValue
                * positionPenalty
                * optimizedGoalkeeperSavingValue
                * attackerAdhocBonus
                * goalkeeperAdhocBonus;

        var random = Math.random();

        log.debug("isGoalFromBigChance - player: {}", player.getName());
        log.debug("isGoalFromBigChance - bigChanceValue: {}", bigChanceValue);
        log.debug("isGoalFromBigChance - fatigueValue: {}", fatigueValue);
        log.debug("isGoalFromBigChance - positionPenalty: {}", positionPenalty);
        log.debug("isGoalFromBigChance - optimizedGoalkeeperSavingValue: {}", optimizedGoalkeeperSavingValue);
        log.debug("isGoalFromBigChance - attackerAdhocBonus2: {}", attackerAdhocBonus);
        log.debug("isGoalFromBigChance - goalkeeperAdhocBonus2: {}", goalkeeperAdhocBonus);
        log.debug("isGoalFromBigChance - result: {}", result);
        log.debug("isGoalFromBigChance - random: {}", random);
        log.debug("------------------------------------------------------------------------------------------------");

        return random <= result;
    }

    @Nonnull
    private Boolean isGoalFromSmallChance(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double goalkeeperSavingBonus
    ) {

        // var smallChanceValue = player.getSmallChanceFinishing() * 0.06;
        var smallChanceValue = player.getSmallChanceFinishing() * 0.12;
        var fatigueValue = 1.0 - ((100 - player.getEnergy()) * 0.00375);
        var positionPenalty = getPossibleOutOfPositionPenalty(player);
        var optimizedGoalkeeperSavingBonus = 1.0 - (goalkeeperSavingBonus / 1.5);
        var goalkeeperAdhocBonus = 1.0 - (Math.random() / 25);
        var attackerAdhocBonus = 1.0 + (Math.random() / 25);

        var result = smallChanceValue
                * fatigueValue
                * positionPenalty
                * optimizedGoalkeeperSavingBonus
                * attackerAdhocBonus
                * goalkeeperAdhocBonus;

        var random = Math.random();

        log.debug("isGoalFromSmallChance - player: {}", player.getName());
        log.debug("isGoalFromSmallChance - smallChanceValue: {}", smallChanceValue);
        log.debug("isGoalFromSmallChance - fatigueValue: {}", fatigueValue);
        log.debug("isGoalFromSmallChance - positionPenalty: {}", positionPenalty);
        log.debug("isGoalFromSmallChance - attackerAdhocBonus: {}", attackerAdhocBonus);
        log.debug("isGoalFromSmallChance - goalkeeperAdhocBonus: {}", goalkeeperAdhocBonus);
        log.debug("isGoalFromSmallChance - optimizedGoalkeeperSavingBonus: {}", optimizedGoalkeeperSavingBonus);
        log.debug("isGoalFromSmallChance - result: {}", result);
        log.debug("isGoalFromSmallChance - random: {}", random);
        log.debug("------------------------------------------------------------------------------------------------");

        return random <= result;
    }

    @Nonnull
    private Double getPossibleOutOfPositionPenalty(@Nonnull SimulatedStarterPlayer player) {

        if (player.getPosition().equals(player.getPrimaryPosition())) {
            return 1.0;
        } else if (positionCheckerService.isPlayingInSecondaryPosition(
                player.getPosition(), player.getOtherPositions())) {
            return 0.95;
        } else if (positionCheckerService.isPlayingInRelatedPosition(
                player.getPosition(), player.getPrimaryPosition())) {
            return 0.9;
        } else {
            return 0.7;
        }
    }

}
