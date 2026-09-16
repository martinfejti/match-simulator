package hu.martinez.matchsimulator.career.team;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    @Nonnull
    public Team map(@Nonnull TeamEntity entity) {
        return new Team(
                entity.getId(),
                entity.getName(),
                entity.getMatchesPlayed(),
                entity.getWins(),
                entity.getDraws(),
                entity.getLosses(),
                entity.getGoalsScored(),
                entity.getGoalsConceded(),
                entity.getPoints()
        );
    }

    @Nonnull
    public TeamEntity mapToEntity(@Nonnull Team team) {
        return new TeamEntity(
                team.id(),
                team.name(),
                team.matchesPlayed(),
                team.wins(),
                team.draws(),
                team.losses(),
                team.goalsScored(),
                team.goalsConceded(),
                team.points()
        );
    }

}
