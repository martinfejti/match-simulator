package hu.martinez.matchsimulator.career.redcard;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class RedCardMapper {

    @Nonnull
    public RedCard map(@Nonnull RedCardEntity entity) {
        return new RedCard(
                entity.getId(),
                entity.getFixtureId(),
                entity.getTeamId(),
                entity.getPlayerId(),
                entity.getExclusionLength()
        );
    }

    @Nonnull
    public RedCardEntity mapToEntity(@Nonnull CreateRedCard createRedCard) {
        return new RedCardEntity(
                null,
                createRedCard.fixtureId(),
                createRedCard.teamId(),
                createRedCard.playerId(),
                createRedCard.exclusionLength()
        );
    }

}
