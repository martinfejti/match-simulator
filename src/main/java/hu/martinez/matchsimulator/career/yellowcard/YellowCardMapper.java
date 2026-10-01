package hu.martinez.matchsimulator.career.yellowcard;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class YellowCardMapper {

    @Nonnull
    public YellowCard map(@Nonnull YellowCardEntity entity) {
        return new YellowCard(
                entity.getId(),
                entity.getFixtureId(),
                entity.getTeamId(),
                entity.getPlayerId()
        );
    }

    @Nonnull
    public YellowCardEntity mapToEntity(@Nonnull CreateYellowCard createYellowCard) {
        return new YellowCardEntity(
                null,
                createYellowCard.fixtureId(),
                createYellowCard.teamId(),
                createYellowCard.playerId()
        );
    }

}
