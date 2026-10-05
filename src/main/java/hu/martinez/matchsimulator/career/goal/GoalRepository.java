package hu.martinez.matchsimulator.career.goal;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<GoalEntity, Integer> {

    @Nonnull
    List<GoalEntity> findByPlayerId(@Nonnull Integer playerId);

    @Nonnull
    Integer countByPlayerIdAndFixtureId(@Nonnull Integer playerId, @Nonnull Integer fixtureId);

}
