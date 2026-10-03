package hu.martinez.matchsimulator.career.simulation.store;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.goal.CreateGoal;
import hu.martinez.matchsimulator.career.goal.GoalService;
import hu.martinez.matchsimulator.career.injury.InjuryService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.redcard.RedCardService;
import hu.martinez.matchsimulator.career.simulation.matchevent.MatchEventContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.team.TeamService;
import hu.martinez.matchsimulator.career.yellowcard.YellowCardService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchResultStoringService {

    private final FixtureService fixtureService;
    private final GoalService goalService;
    private final InjuryService injuryService;
    private final PlayerService playerService;
    private final RedCardService redCardService;
    private final TeamService teamService;
    private final YellowCardService yellowCardService;

    @Transactional
    public void storeMatchResult(
            @Nonnull MatchEventContainer matchEventContainer,
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer
    ) {

        // goals
        List<CreateGoal> createGoalList = new ArrayList<>();
        createGoalList.addAll(matchEventContainer.goalsContainer().homeTeamGoalList());
        createGoalList.addAll(matchEventContainer.goalsContainer().awayTeamGoalList());

        if (!createGoalList.isEmpty()) {
            goalService.saveGoalsForFixture(createGoalList);
        }

        // yellow cards
        if (!matchEventContainer.yellowCardList().isEmpty()) {
            yellowCardService.saveYellowCards(matchEventContainer.yellowCardList());
        }

        // red cards
        if (!matchEventContainer.redCardList().isEmpty()) {
            redCardService.saveRedCards(matchEventContainer.redCardList());
        }

        // injuries
        if (!matchEventContainer.injuryList().isEmpty()) {
            injuryService.saveAllInjuries(matchEventContainer.injuryList());
        }

        // fixture
        fixtureService.saveMatchResults(fixtureDataContainer.simulatedFixture());

        // teams
        teamService.saveMatchResults(fixtureDataContainer.homeTeamDataContainer().team());
        teamService.saveMatchResults(fixtureDataContainer.awayTeamDataContainer().team());

        // starter players
        fixtureDataContainer.homeTeamDataContainer().forwardList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        fixtureDataContainer.homeTeamDataContainer().midfielderList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        fixtureDataContainer.homeTeamDataContainer().defenderList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        playerService.saveMatchResultsForStarterPlayer(fixtureDataContainer.homeTeamDataContainer().goalkeeper());

        fixtureDataContainer.awayTeamDataContainer().forwardList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        fixtureDataContainer.awayTeamDataContainer().midfielderList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        fixtureDataContainer.awayTeamDataContainer().defenderList()
                .forEach(playerService::saveMatchResultsForStarterPlayer);
        playerService.saveMatchResultsForStarterPlayer(fixtureDataContainer.awayTeamDataContainer().goalkeeper());

        // benched players
        fixtureDataContainer.homeTeamDataContainer().benchedPlayerList()
                .forEach(playerService::saveMatchResultsForBenchedPlayer);
        fixtureDataContainer.awayTeamDataContainer().benchedPlayerList()
                .forEach(playerService::saveMatchResultsForBenchedPlayer);
    }

}
