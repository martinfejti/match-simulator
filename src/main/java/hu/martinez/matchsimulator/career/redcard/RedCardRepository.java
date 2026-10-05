package hu.martinez.matchsimulator.career.redcard;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RedCardRepository extends JpaRepository<RedCardEntity, Integer> {

    @Nonnull
    List<RedCardEntity> findByPlayerId(@Nonnull Integer playerId);

    @Query("""
        SELECT COALESCE(MAX(r.exclusionLength), 0) 
        FROM RedCardEntity r 
        WHERE r.playerId = :playerId 
          AND r.fixtureId = :fixtureId
    """)
    int getExclusionLengthByPlayerAndFixture(
            @Param("playerId") Integer playerId,
            @Param("fixtureId") Integer fixtureId
    );

}
