package hu.martinez.matchsimulator.career.simulation.matchevent.redcard;

import hu.martinez.matchsimulator.career.redcard.CreateRedCard;
import hu.martinez.matchsimulator.career.simulation.matchevent.PositionCheckerService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class SimulationRedCardService {

    private final PositionCheckerService positionCheckerService;

    @Nonnull
    public List<CreateRedCard> handleRedCards(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        List<CreateRedCard> redCardList = new ArrayList<>();

        redCardList.addAll(
                handleRedCardsForTeam(
                        fixtureDataContainer.homeTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));
        redCardList.addAll(
                handleRedCardsForTeam(
                        fixtureDataContainer.awayTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));

        addRedCardsToFixture(redCardList, fixtureDataContainer);

        return redCardList;
    }

    @Nonnull
    private List<CreateRedCard> handleRedCardsForTeam(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer,
            @Nonnull Integer fixtureId
    ) {

        List<Optional<CreateRedCard>> redCardList = new ArrayList<>();

        redCardList.addAll(
                teamDataContainer.forwardList()
                        .stream()
                        .map(player -> handleRedCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        redCardList.addAll(
                teamDataContainer.midfielderList()
                        .stream()
                        .map(player -> handleRedCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        redCardList.addAll(
                teamDataContainer.defenderList()
                        .stream()
                        .map(player -> handleRedCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        redCardList.add(handleRedCardForPlayer(
                teamDataContainer.goalkeeper(), teamDataContainer.formation(), fixtureId));

        return redCardList
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Nonnull
    private Optional<CreateRedCard> handleRedCardForPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull String formation,
            @Nonnull Integer fixtureId
    ) {

        if (isRedCardForPlayer(starterPlayer, getRedCardChanceForPlayer(starterPlayer, formation))) {

            starterPlayer.setNumberOfRedCards(starterPlayer.getNumberOfRedCards() + 1);

            var exclusionLengthValue = Math.random();
            var exclusionLength = 0;
            if (exclusionLengthValue <= 0.5) {
                exclusionLength = 1;
            } else if (exclusionLengthValue <= 0.8) {
                exclusionLength = 2;
            } else {
                exclusionLength = 3;
            }

            starterPlayer.setExcludedFor(exclusionLength);

            log.debug("handleRedCardForPlayer - RED for {} for {} matches",
                    starterPlayer.getName(), starterPlayer.getExcludedFor());

            return Optional.of(
                    new CreateRedCard(fixtureId, starterPlayer.getTeamId(), starterPlayer.getId(), exclusionLength)
            );
        }

        return Optional.empty();
    }

    private void addRedCardsToFixture(
            @Nonnull List<CreateRedCard> redCardList,
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer
    ) {

        for (var redCard : redCardList) {
            if (redCard.teamId().equals(fixtureDataContainer.simulatedFixture().getHomeTeamId())) {
                fixtureDataContainer.simulatedFixture().setHomeRedCards(
                        fixtureDataContainer.simulatedFixture().getHomeRedCards() + 1
                );
            } else {
                fixtureDataContainer.simulatedFixture().setAwayRedCards(
                        fixtureDataContainer.simulatedFixture().getAwayRedCards() + 1
                );
            }
        }
    }

    @Nonnull
    private Boolean isRedCardForPlayer(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double chanceForRedCard
    ) {

        double modifiedChance = chanceForRedCard;

        // energy-related penalty
        int energy = player.getEnergy();
        if (energy < 70) {
            int drop = 70 - energy;
            int steps = drop / 10;
            modifiedChance *= (1.0 + (steps * 0.1));
        }

        // position-related penalty
        if (!player.getPosition().equals(player.getPrimaryPosition())) {
            if (positionCheckerService.isPlayingInSecondaryPosition(
                    player.getPosition(), player.getOtherPositions())) {
                modifiedChance *= 1.1;
            } else if (positionCheckerService.isPlayingInRelatedPosition(
                    player.getPosition(), player.getPrimaryPosition())) {
                modifiedChance *= 1.25;
            } else {
                modifiedChance *= 1.5;
            }
        }

        modifiedChance = Math.max(0.0, Math.min(1.0, modifiedChance));

        return ThreadLocalRandom.current().nextDouble() < modifiedChance;
    }

    @Nonnull
    private Double getRedCardChanceForPlayer(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull String formation
    ) {

        if ("4-3-3".equals(formation)) {
            return getRedCardChanceForPlayerIn433Formation(player);
        } else if ("4-4-2".equals(formation)) {
            return getRedCardChanceForPlayerIn442Formation(player);
        } else if ("4-2-3-1".equals(formation)) {
            return getRedCardChanceForPlayerIn4231Formation(player);
        } else if ("3-5-2".equals(formation)) {
            return getRedCardChanceForPlayerIn352Formation(player);
        } else if ("5-3-2".equals(formation)) {
            return getRedCardChanceForPlayerIn532Formation(player);
        } else {
            throw new IllegalStateException("Unknown formation!");
        }
    }

    @Nonnull
    private Double getRedCardChanceForPlayerIn433Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.001;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.005;
        } else if ("CB".equals(player.getPosition())) {
            return 0.011;
        } else if ("CDM".equals(player.getPosition())) {
            return 0.010;
        } else if ("CM".equals(player.getPosition())) {
            return 0.004;
        } else if ("RW".equals(player.getPosition()) || "LW".equals(player.getPosition())) {
            return 0.0015;
        } else if ("ST".equals(player.getPosition())) {
            return 0.002;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getRedCardChanceForPlayerIn442Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.001;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.005;
        } else if ("CB".equals(player.getPosition())) {
            return 0.011;
        } else if ("CM".equals(player.getPosition())) {
            return 0.004;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.003;
        } else if ("ST".equals(player.getPosition())) {
            return 0.002;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getRedCardChanceForPlayerIn4231Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.001;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.005;
        } else if ("CB".equals(player.getPosition())) {
            return 0.011;
        } else if ("CDM".equals(player.getPosition())) {
            return 0.010;
        } else if ("CAM".equals(player.getPosition())) {
            return 0.0015;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.0025;
        } else if ("ST".equals(player.getPosition())) {
            return 0.002;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getRedCardChanceForPlayerIn352Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.001;
        } else if ("CB".equals(player.getPosition())) {
            return 0.011;
        } else if ("CM".equals(player.getPosition())) {
            return 0.0045;
        } else if ("CAM".equals(player.getPosition())) {
            return 0.0015;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.0035;
        } else if ("ST".equals(player.getPosition())) {
            return 0.002;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getRedCardChanceForPlayerIn532Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.001;
        } else if ("RWB".equals(player.getPosition()) || "LWB".equals(player.getPosition())) {
            return 0.0045;
        } else if ("CB".equals(player.getPosition())) {
            return 0.01;
        } else if ("CM".equals(player.getPosition())) {
            return 0.004;
        } else if ("ST".equals(player.getPosition())) {
            return 0.0018;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

}
