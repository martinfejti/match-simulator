package hu.martinez.matchsimulator.career.fixture;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FixtureRepository extends JpaRepository<FixtureEntity, Integer> {

    @Nonnull
    List<FixtureEntity> findByMatchWeekOrderByMatchNumberInWeekAsc(@Nonnull Integer matchWeek);

    @Query("""
        SELECT f FROM FixtureEntity f 
        WHERE f.matchWeek = (
            SELECT MIN(f2.matchWeek) 
            FROM FixtureEntity f2 
            WHERE f2.isFinished = false
        )
        ORDER BY f.matchNumberInWeek ASC
    """)
    List<FixtureEntity> findNextUnfinishedMatchWeekFixtures();

    @Query("""
        SELECT f FROM FixtureEntity f 
        WHERE f.matchWeek = (SELECT MAX(f2.matchWeek) FROM FixtureEntity f2)
        ORDER BY f.matchNumberInWeek ASC
    """)
    List<FixtureEntity> findFinalMatchWeekFixtures();

    @Query("""
        SELECT f FROM FixtureEntity f 
        WHERE f.homeTeam.id = :teamId OR f.awayTeam.id = :teamId 
        ORDER BY f.matchWeek ASC
    """)
    List<FixtureEntity> findAllByTeamId(@Param("teamId") Integer teamId);

}
