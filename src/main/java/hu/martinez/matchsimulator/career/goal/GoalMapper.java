package hu.martinez.matchsimulator.career.goal;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class GoalMapper {

    @Nonnull
    public Goal map(@Nonnull GoalEntity entity) {
        return new Goal(
                entity.getId(),
                entity.getFixtureId(),
                entity.getTeamId(),
                entity.getPlayerId()
        );
    }

    @Nonnull
    public GoalEntity mapToEntity(@Nonnull CreateGoal createGoal) {
        return new GoalEntity(
                null,
                createGoal.fixtureId(),
                createGoal.teamId(),
                createGoal.playerId()
        );
    }

}
