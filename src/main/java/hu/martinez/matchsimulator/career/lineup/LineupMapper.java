package hu.martinez.matchsimulator.career.lineup;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class LineupMapper {

    @Nonnull
    public Lineup map(@Nonnull LineupEntity entity) {
        return new Lineup(
                entity.getId(),
                entity.getFixtureId(),
                entity.getTeamId(),
                entity.getPlayerId(),
                entity.getPosition()
        );
    }

    @Nonnull
    public LineupEntity mapToEntity(@Nonnull CreateLineup.StarterPlayer starterPlayer) {
        return new LineupEntity(
                null,
                starterPlayer.fixtureId(),
                starterPlayer.teamId(),
                starterPlayer.playerId(),
                starterPlayer.position()
        );
    }

}
