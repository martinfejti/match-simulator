package hu.martinez.matchsimulator.career.injury;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InjuryRepository extends JpaRepository<InjuryEntity, Integer> {

    @Nonnull
    List<InjuryEntity> findByPlayerId(@Nonnull Integer playerId);

    @Nonnull
    @Query("""
        SELECT COALESCE(MAX(i.injuryLength), 0) 
        FROM InjuryEntity i 
        WHERE i.playerId = :playerId 
          AND i.fixtureId = :fixtureId
    """)
    Integer getInjuryLengthByPlayerAndFixture(
            @Param("playerId") Integer playerId,
            @Param("fixtureId") Integer fixtureId
    );

    @Nonnull
    Integer countByTeamIdAndFixtureId(@Nonnull Integer teamId, @Nonnull Integer fixtureId);

    @Nonnull
    List<InjuryEntity> findByFixtureId(@Nonnull Integer fixtureId);

}
