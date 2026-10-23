package hu.martinez.matchsimulator.career.simulation.matchevent.yellowcard;

import hu.martinez.matchsimulator.career.simulation.matchevent.PositionCheckerService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import hu.martinez.matchsimulator.career.yellowcard.CreateYellowCard;
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
public class SimulationYellowCardService {

    private final PositionCheckerService positionCheckerService;

    @Nonnull
    public List<CreateYellowCard> handleYellowCards(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        List<CreateYellowCard> yellowCardList = new ArrayList<>();

        yellowCardList.addAll(
                handleYellowCards(
                        fixtureDataContainer.homeTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));
        yellowCardList.addAll(
                handleYellowCards(
                        fixtureDataContainer.awayTeamDataContainer(), fixtureDataContainer.simulatedFixture().getId()));

        addYellowCardsToFixture(yellowCardList, fixtureDataContainer);

        return yellowCardList;
    }

    @Nonnull
    private List<CreateYellowCard> handleYellowCards(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer,
            @Nonnull Integer fixtureId
    ) {

        List<Optional<CreateYellowCard>> yellowCardList = new ArrayList<>();

        yellowCardList.addAll(
                teamDataContainer.forwardList().stream()
                        .map(player -> handleYellowCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        yellowCardList.addAll(
                teamDataContainer.midfielderList().stream()
                        .map(player -> handleYellowCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        yellowCardList.addAll(
                teamDataContainer.defenderList().stream()
                        .map(player -> handleYellowCardForPlayer(player, teamDataContainer.formation(), fixtureId))
                        .toList()
        );
        yellowCardList.add(handleYellowCardForPlayer(
                teamDataContainer.goalkeeper(), teamDataContainer.formation(), fixtureId));

        return yellowCardList
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Nonnull
    private Optional<CreateYellowCard> handleYellowCardForPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull String formation,
            @Nonnull Integer fixtureId
    ) {

        if (isYellowCardForPlayer(starterPlayer, getYellowCardChanceForPlayer(starterPlayer, formation))) {
            log.debug("handleYellowCardForPlayer - YELLOW for: {}", starterPlayer.getName());

            starterPlayer.setNumberOfYellowCards(starterPlayer.getNumberOfYellowCards() + 1);

            if (starterPlayer.getNumberOfYellowCards() % 5 == 0) {
                log.debug("handleYellowCardForPlayer - Excluded for too many yellow cards: {}", starterPlayer.getName());
                starterPlayer.setExcludedFor(1);
            }

            return Optional.of(new CreateYellowCard(fixtureId, starterPlayer.getTeamId(), starterPlayer.getId()));
        }

        return Optional.empty();
    }

    @Nonnull
    private Boolean isYellowCardForPlayer(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull Double chanceForYellowCard
    ) {

        double modifiedChance = chanceForYellowCard;

        // energy-related penalty
        int energy = player.getEnergy();
        if (energy < 70) {
            int drop = 70 - energy;
            int steps = drop / 10;
            modifiedChance *= (1.0 + (steps * 0.05));
        }

        // position-related penalty
        if (!player.getPosition().equals(player.getPrimaryPosition())) {
            if (positionCheckerService.isPlayingInSecondaryPosition(
                    player.getPosition(), player.getOtherPositions())) {
                modifiedChance *= 1.05;
            } else if (positionCheckerService.isPlayingInRelatedPosition(
                    player.getPosition(), player.getPrimaryPosition())) {
                modifiedChance *= 1.10;
            } else {
                modifiedChance *= 1.20;
            }
        }

        modifiedChance = Math.max(0.0, Math.min(1.0, modifiedChance));

        return ThreadLocalRandom.current().nextDouble() < modifiedChance;
    }

    private void addYellowCardsToFixture(
            @Nonnull List<CreateYellowCard> yellowCardList,
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer
    ) {

        for (var yellowCard : yellowCardList) {
            if (yellowCard.teamId().equals(fixtureDataContainer.simulatedFixture().getHomeTeamId())) {
                fixtureDataContainer.simulatedFixture().setHomeYellowCards(
                        fixtureDataContainer.simulatedFixture().getHomeYellowCards() + 1
                );
            } else {
                fixtureDataContainer.simulatedFixture().setAwayYellowCards(
                        fixtureDataContainer.simulatedFixture().getAwayYellowCards() + 1
                );
            }
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayer(
            @Nonnull SimulatedStarterPlayer player,
            @Nonnull String formation
    ) {

        if ("4-3-3".equals(formation)) {
            return getYellowCardChanceForPlayerIn433Formation(player);
        } else if ("4-4-2".equals(formation)) {
            return getYellowCardChanceForPlayerIn442Formation(player);
        } else if ("4-2-3-1".equals(formation)) {
            return getYellowCardChanceForPlayerIn4231Formation(player);
        } else if ("3-5-2".equals(formation)) {
            return getYellowCardChanceForPlayerIn352Formation(player);
        } else if ("5-3-2".equals(formation)) {
            return getYellowCardChanceForPlayerIn532Formation(player);
        } else {
            throw new IllegalStateException("Unknown formation!");
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayerIn433Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.02;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.13;
        } else if ("CB".equals(player.getPosition())) {
            return 0.17;
        } else if ("CDM".equals(player.getPosition())) {
            return 0.19;
        } else if ("CM".equals(player.getPosition())) {
            return 0.12;
        } else if ("RW".equals(player.getPosition()) || "LW".equals(player.getPosition())) {
            return 0.05;
        } else if ("ST".equals(player.getPosition())) {
            return 0.05;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayerIn442Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.02;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.13;
        } else if ("CB".equals(player.getPosition())) {
            return 0.17;
        } else if ("CM".equals(player.getPosition())) {
            return 0.12;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.08;
        } else if ("ST".equals(player.getPosition())) {
            return 0.05;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayerIn4231Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.02;
        } else if ("RB".equals(player.getPosition()) || "LB".equals(player.getPosition())) {
            return 0.13;
        } else if ("CB".equals(player.getPosition())) {
            return 0.17;
        } else if ("CDM".equals(player.getPosition())) {
            return 0.19;
        } else if ("CAM".equals(player.getPosition())) {
            return 0.07;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.06;
        } else if ("ST".equals(player.getPosition())) {
            return 0.05;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayerIn352Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.02;
        } else if ("CB".equals(player.getPosition())) {
            return 0.17;
        } else if ("CM".equals(player.getPosition())) {
            return 0.14;
        } else if ("CAM".equals(player.getPosition())) {
            return 0.08;
        } else if ("RM".equals(player.getPosition()) || "LM".equals(player.getPosition())) {
            return 0.11;
        } else if ("ST".equals(player.getPosition())) {
            return 0.05;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

    @Nonnull
    private Double getYellowCardChanceForPlayerIn532Formation(@Nonnull SimulatedStarterPlayer player) {

        if ("GK".equals(player.getPosition())) {
            return 0.02;
        } else if ("RWB".equals(player.getPosition()) || "LWB".equals(player.getPosition())) {
            return 0.12;
        } else if ("CB".equals(player.getPosition())) {
            return 0.17;
        } else if ("CM".equals(player.getPosition())) {
            return 0.14;
        } else if ("ST".equals(player.getPosition())) {
            return 0.04;
        } else {
            throw new IllegalStateException("Unknown position!");
        }
    }

}
