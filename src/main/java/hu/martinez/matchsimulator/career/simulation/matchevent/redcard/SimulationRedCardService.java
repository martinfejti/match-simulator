package hu.martinez.matchsimulator.career.simulation.matchevent.redcard;

import hu.martinez.matchsimulator.career.redcard.CreateRedCard;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class SimulationRedCardService {

    // TODO collect real life data
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
                        .map(player -> handleRedCardForPlayer(player, 0.0025, fixtureId))
                        .toList()
        );
        redCardList.addAll(
                teamDataContainer.midfielderList()
                        .stream()
                        .map(player -> handleRedCardForPlayer(player, 0.005, fixtureId))
                        .toList()
        );
        redCardList.addAll(
                teamDataContainer.defenderList()
                        .stream()
                        .map(player -> handleRedCardForPlayer(player, 0.01, fixtureId))
                        .toList()
        );
        redCardList.add(handleRedCardForPlayer(teamDataContainer.goalkeeper(), 0.001, fixtureId));

        return redCardList
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Nonnull
    private Optional<CreateRedCard> handleRedCardForPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull Double chanceOfGettingRedCard,
            @Nonnull Integer fixtureId
    ) {

        if (Math.random() < chanceOfGettingRedCard) {

            starterPlayer.setNumberOfRedCards(starterPlayer.getNumberOfRedCards() + 1);

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
                        fixtureDataContainer.simulatedFixture().getAwayRedCards() + 1
                );
            } else {
                fixtureDataContainer.simulatedFixture().setAwayRedCards(
                        fixtureDataContainer.simulatedFixture().getAwayRedCards() + 1
                );
            }
        }
    }

}
