package hu.martinez.matchsimulator.career.standing;

import hu.martinez.matchsimulator.career.player.Player;
import hu.martinez.matchsimulator.career.team.TeamService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StandingsPlayerStatisticsMapper {

    private final TeamService teamService;

    @Nonnull
    public List<StandingsGoalScorer> mapGoalScorers(@Nonnull List<Player> playerList) {
        return playerList
                .stream()
                .map(player -> new StandingsGoalScorer(
                        player.id(),
                        player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                        player.nationality(),
                        teamService.getTeamById(player.teamId()).name(),
                        player.numberOfGoals()
                ))
                .toList();
    }

    @Nonnull
    public List<StandingsCleanSheet> mapCleanSheets(@Nonnull List<Player> playerList) {
        return playerList
                .stream()
                .map(player -> new StandingsCleanSheet(
                        player.id(),
                        player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                        player.nationality(),
                        teamService.getTeamById(player.teamId()).name(),
                        player.cleanSheets()
                ))
                .toList();
    }

    @Nonnull
    public List<StandingsYellowCard> mapYellowCards(@Nonnull List<Player> playerList) {
        return playerList
                .stream()
                .map(player -> new StandingsYellowCard(
                        player.id(),
                        player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                        player.nationality(),
                        teamService.getTeamById(player.teamId()).name(),
                        player.numberOfYellowCards()
                ))
                .toList();
    }

    @Nonnull
    public List<StandingsRedCard> mapRedCards(@Nonnull List<Player> playerList) {
        return playerList
                .stream()
                .map(player -> new StandingsRedCard(
                        player.id(),
                        player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                        player.nationality(),
                        teamService.getTeamById(player.teamId()).name(),
                        player.numberOfRedCards()
                ))
                .toList();
    }

}
