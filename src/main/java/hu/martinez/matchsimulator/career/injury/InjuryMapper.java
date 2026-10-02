package hu.martinez.matchsimulator.career.injury;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class InjuryMapper {

    @Nonnull
    public Injury map(@Nonnull InjuryEntity entity) {
        return new Injury(
                entity.getId(),
                entity.getFixtureId(),
                entity.getTeamId(),
                entity.getPlayerId(),
                entity.getInjuryLength()
        );
    }

    @Nonnull
    public InjuryEntity mapToEntity(@Nonnull CreateInjury createInjury) {
        return new InjuryEntity(
                null,
                createInjury.fixtureId(),
                createInjury.teamId(),
                createInjury.playerId(),
                createInjury.injuryLength()
        );
    }

}
