package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.TeamEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FixtureRepository extends JpaRepository<FixtureEntity, Integer> {

    @Nonnull
    Integer countByIsFinishedTrue();

    @Nonnull
    Integer countByIsFinishedFalse();

    @Nonnull
    List<FixtureEntity> findByMatchWeekOrderByMatchNumberInWeekAsc(@Nonnull Integer matchWeek);

    @Query("SELECT f FROM FixtureEntity f " +
            "WHERE f.isFinished = false " +
            "ORDER BY f.matchWeek ASC, f.matchNumberInWeek ASC " +
            "LIMIT 1")
    Optional<FixtureEntity> findNextUpcomingFixture();

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

    @Query("""
        SELECT f FROM FixtureEntity f 
        WHERE (f.homeTeam = :team OR f.awayTeam = :team) 
          AND f.isFinished = true 
        ORDER BY f.matchWeek DESC
        LIMIT 1
    """)
    Optional<FixtureEntity> findLastFinishedFixtureForTeam(@Param("team") TeamEntity team);

    @Modifying
    @Query("UPDATE FixtureEntity f SET f.homeFormation = :formation WHERE f.id = :fixtureId")
    void updateHomeFormation(@Param("fixtureId") Integer fixtureId, @Param("formation") String formation);

    @Modifying
    @Query("UPDATE FixtureEntity f SET f.awayFormation = :formation WHERE f.id = :fixtureId")
    void updateAwayFormation(@Param("fixtureId") Integer fixtureId, @Param("formation") String formation);

}
