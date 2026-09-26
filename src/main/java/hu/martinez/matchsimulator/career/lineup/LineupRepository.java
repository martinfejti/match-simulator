package hu.martinez.matchsimulator.career.lineup;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LineupRepository extends JpaRepository<LineupEntity, Integer> {

    @Nonnull
    List<LineupEntity> findByFixtureIdAndTeamId(@Nonnull Integer fixtureId, @Nonnull Integer teamId);

}
