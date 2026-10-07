package hu.martinez.matchsimulator.career.matchresult;

import hu.martinez.matchsimulator.career.fixture.Fixture;
import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.goal.GoalService;
import hu.martinez.matchsimulator.career.injury.InjuryService;
import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.redcard.RedCardService;
import hu.martinez.matchsimulator.career.team.TeamService;
import hu.martinez.matchsimulator.career.yellowcard.YellowCardService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchResultService {

    private final FixtureService fixtureService;
    private final GoalService goalService;
    private final InjuryService injuryService;
    private final LineupService lineupService;
    private final PlayerService playerService;
    private final RedCardService redCardService;
    private final TeamService teamService;
    private final YellowCardService yellowCardService;

    @Nonnull
    public List<MatchResultPlayer> getMatchResultPlayerList(
            @Nonnull Integer fixtureId,
            @Nonnull Integer teamId
    ) {

        List<MatchResultPlayer> matchResultPlayerList = new ArrayList<>();
        var lineupList = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, teamId);

        for (var lineup : lineupList) {

            var player = playerService.getPlayerById(lineup.playerId());

            matchResultPlayerList.add(
                    new MatchResultPlayer(
                            player.id(),
                            player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                            player.shirtNumber(),
                            player.nationality(),
                            player.overall(),
                            lineup.position(),
                            goalService.countGoalsByPlayerIdAndFixtureId(player.id(), fixtureId),
                            yellowCardService.existsYellowCardByPlayerIdAndFixtureId(player.id(), fixtureId),
                            redCardService.getExclusionLengthByPlayerAndFixture(player.id(), fixtureId),
                            injuryService.getInjuryLengthByPlayerAndFixture(player.id(), fixtureId)
                    )
            );
        }

        return matchResultPlayerList;
    }

    @Nonnull
    public List<MatchResultEvent> getMatchResultEventList(@Nonnull Integer fixtureId) {

        List<MatchResultEvent> matchResultEventList = new ArrayList<>();

        var fixture = fixtureService.getFixtureById(fixtureId);

        // goals
        var goalList = goalService.getAllGoalsByFixtureId(fixtureId);
        for (var goal : goalList) {
            var player = playerService.getPlayerById(goal.playerId());
            matchResultEventList.add(
                    new MatchResultEvent(
                            player.lastName(),
                            goal.teamId().equals(fixture.homeTeam().id()),
                            "Goal",
                            null,
                            null
                    )
            );
        }

        // yellow cards
        var yellowCardList = yellowCardService.getAllYellowCardsByFixtureId(fixtureId);
        for (var yellowCard : yellowCardList) {
            var player = playerService.getPlayerById(yellowCard.playerId());
            matchResultEventList.add(
                    new MatchResultEvent(
                            player.lastName(),
                            yellowCard.teamId().equals(fixture.homeTeam().id()),
                            "Yellow Card",
                            null,
                            null
                    )
            );
        }

        // red cards
        var redCardList = redCardService.getAllRedCardsByFixtureId(fixtureId);
        for (var redCard : redCardList) {
            var player = playerService.getPlayerById(redCard.playerId());
            matchResultEventList.add(
                    new MatchResultEvent(
                            player.lastName(),
                            redCard.teamId().equals(fixture.homeTeam().id()),
                            "Red Card",
                            redCard.exclusionLength(),
                            null
                    )
            );
        }

        // injuries
        var injuryList = injuryService.getAllInjuriesByFixtureId(fixtureId);
        for (var injury : injuryList) {
            var player = playerService.getPlayerById(injury.playerId());
            matchResultEventList.add(
                    new MatchResultEvent(
                            player.lastName(),
                            injury.teamId().equals(fixture.homeTeam().id()),
                            "Injury",
                            null,
                            injury.injuryLength()
                    )
            );
        }

        Collections.shuffle(matchResultEventList);

        return matchResultEventList;
    }

    @Nonnull
    public MatchResultAverageContainer getStartingElevenAverages(@Nonnull Integer fixtureId) {

        var fixture = fixtureService.getFixtureById(fixtureId);

        // home team
        var homeSum = 0.0;
        var homeLineupList = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.homeTeam().id());
        for (var homeLineup : homeLineupList) {
            var player = playerService.getPlayerById(homeLineup.playerId());
            homeSum += player.overall();
        }

        // away team
        var awaySum = 0.0;
        var awayLineupList = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.awayTeam().id());
        for (var awayLineup : awayLineupList) {
            var player = playerService.getPlayerById(awayLineup.playerId());
            awaySum += player.overall();
        }

        return new MatchResultAverageContainer(
                homeSum / 11.0,
                awaySum / 11.0
        );
    }

    @Nonnull
    public MatchResultFormContainer getRecentForms(@Nonnull Integer fixtureId) {

        var currentFixture = fixtureService.getFixtureById(fixtureId);

        return new MatchResultFormContainer(
                getRecentFormForTeam(currentFixture.homeTeam().id(), currentFixture),
                getRecentFormForTeam(currentFixture.awayTeam().id(), currentFixture)
        );
    }

    @Nonnull
    private List<MatchResultForm> getRecentFormForTeam(@Nonnull Integer teamId, @Nonnull Fixture currentFixture) {

        List<MatchResultForm> homeFormList = new ArrayList<>();

        var recentFixtureList = fixtureService.getRecentFixturesForTeam(
                teamId, currentFixture.matchWeek());
        for (var recentFixture : recentFixtureList) {

            var isHome = recentFixture.homeTeam().id().equals(teamId);
            var teamScore = isHome ? recentFixture.homeScore() : recentFixture.awayScore();
            var opponentScore = isHome ? recentFixture.awayScore() : recentFixture.homeScore();

            String outcome;
            if (teamScore > opponentScore) {
                outcome = "W";
            } else if (opponentScore > teamScore) {
                outcome = "L";
            } else {
                outcome = "D";
            }

            var homeTeam = teamService.getTeamById(recentFixture.homeTeam().id());
            var awayTeam = teamService.getTeamById(recentFixture.awayTeam().id());

            homeFormList.add(
                    new MatchResultForm(
                            outcome,
                            String.format("Matchweek %s: %s %s - %s %s",
                                    recentFixture.matchWeek(),
                                    homeTeam.name(),
                                    recentFixture.homeScore(),
                                    recentFixture.awayScore(),
                                    awayTeam.name()
                            )
                    )
            );
        }

        return homeFormList;
    }

}
