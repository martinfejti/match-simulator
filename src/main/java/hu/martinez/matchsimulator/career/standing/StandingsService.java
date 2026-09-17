package hu.martinez.matchsimulator.career.standing;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.team.Team;
import hu.martinez.matchsimulator.career.team.TeamService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StandingsService {

    private final FixtureService fixtureService;
    private final TeamService teamService;

    @Nonnull
    public List<Standing> getStandings() {

        var teamList = teamService.getOrderedTeamListForStandings();

        return teamList.stream()
                .map(team -> {

                    var lastMatchResult = getLastMatchResult(team);

                    return new Standing(
                            team.id(),
                            team.name(),
                            team.matchesPlayed(),
                            team.wins(),
                            team.draws(),
                            team.losses(),
                            team.points(),
                            team.goalsScored() + " - " + team.goalsConceded(),
                            team.goalsScored() - team.goalsConceded(),
                            lastMatchResult
                    );
                })
                .toList();
    }

    @Nonnull
    private String getLastMatchResult(@Nonnull Team team) {

        var lastMatchResult = fixtureService.getLastFinishedFixtureForTeam(team);

        if (lastMatchResult.isEmpty()) {
            return "-";
        }

        var fixture = lastMatchResult.get();
        var isHome = fixture.homeTeam().id().equals(team.id());
        var teamGoals = isHome ? fixture.homeScore() : fixture.awayScore();
        var opponentGoals = isHome ? fixture.awayScore() : fixture.homeScore();

        if (teamGoals > opponentGoals) {
            return "W";
        } else if (teamGoals < opponentGoals) {
            return "L";
        } else {
            return "D";
        }
    }

}
