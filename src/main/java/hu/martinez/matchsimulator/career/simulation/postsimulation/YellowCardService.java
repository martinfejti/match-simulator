package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import hu.martinez.matchsimulator.career.yellowcard.CreateYellowCard;
import jakarta.annotation.Nonnull;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class YellowCardService {

    @Nonnull
    public List<CreateYellowCard> handleYellowCards(
            @Nonnull PreSimulationTeamDataContainer homeTeamDataContainer,
            @Nonnull PreSimulationTeamDataContainer awayTeamDataContainer,
            @Nonnull Integer fixtureId
    ) {

        List<CreateYellowCard> yellowCardList = new ArrayList<>();

        yellowCardList.addAll(handleYellowCards(homeTeamDataContainer, fixtureId));
        yellowCardList.addAll(handleYellowCards(awayTeamDataContainer, fixtureId));

        return yellowCardList;
    }

    @Nonnull
    private List<CreateYellowCard> handleYellowCards(
            @Nonnull PreSimulationTeamDataContainer teamDataContainer,
            @Nonnull Integer fixtureId
    ) {

        List<Optional<CreateYellowCard>> yellowCardList = new ArrayList<>();

        yellowCardList.addAll(
                teamDataContainer.forwardList()
                        .stream()
                        .map(player -> handleYellowCardForPlayer(player, 0.05, fixtureId))
                        .toList()
        );
        yellowCardList.addAll(
                teamDataContainer.midfielderList()
                        .stream()
                        .map(player -> handleYellowCardForPlayer(player, 0.1, fixtureId))
                        .toList()
        );
        yellowCardList.addAll(
                teamDataContainer.defenderList()
                        .stream()
                        .map(player -> handleYellowCardForPlayer(player, 0.15, fixtureId))
                        .toList());
        yellowCardList.add(handleYellowCardForPlayer(teamDataContainer.goalkeeper(), 0.025, fixtureId));

        return yellowCardList
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Nonnull
    private Optional<CreateYellowCard> handleYellowCardForPlayer(
            @Nonnull SimulatedStarterPlayer starterPlayer,
            @Nonnull Double chanceOfGettingYellowCard,
            @Nonnull Integer fixtureId
    ) {

        if (Math.random() < chanceOfGettingYellowCard) {
            log.debug("handleYellowCardForPlayer - YELLOW for: {}", starterPlayer.getName());
            starterPlayer.setNumberOfYellowCards(starterPlayer.getNumberOfYellowCards() + 1);

            return Optional.of(new CreateYellowCard(fixtureId, starterPlayer.getTeamId(), starterPlayer.getId()));
        }

        if (starterPlayer.getNumberOfYellowCards() != 0 && starterPlayer.getNumberOfYellowCards() % 5 == 0) {
            log.debug("handleYellowCardForPlayer - Excluded for too many yellow cards: {}", starterPlayer.getName());
            starterPlayer.setExcludedFor(1);
        }

        return Optional.empty();
    }

}
