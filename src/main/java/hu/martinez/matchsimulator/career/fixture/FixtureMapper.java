package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.TeamMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FixtureMapper {

    private final TeamMapper teamMapper;

    @Nonnull
    public Fixture map(@Nonnull FixtureEntity entity) {
        return new Fixture(
                entity.getId(),
                entity.getMatchWeek(),
                entity.getMatchNumberInWeek(),
                teamMapper.map(entity.getHomeTeam()),
                teamMapper.map(entity.getAwayTeam()),
                entity.getMatchDate(),
                entity.getHomeScore(),
                entity.getAwayScore(),
                entity.getHomeBigChances(),
                entity.getHomeSmallChances(),
                entity.getHomeYellowCards(),
                entity.getHomeRedCards(),
                entity.getAwayBigChances(),
                entity.getAwaySmallChances(),
                entity.getAwayYellowCards(),
                entity.getAwayRedCards(),
                entity.isFinished()
        );
    }

}
