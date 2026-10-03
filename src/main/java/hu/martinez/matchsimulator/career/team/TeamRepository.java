package hu.martinez.matchsimulator.career.team;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {

    @Nonnull
    @Query("""
        SELECT t FROM TeamEntity t 
        ORDER BY 
            t.points DESC,
            (t.goalsScored - t.goalsConceded) DESC,
            t.goalsScored DESC,
            t.wins DESC,
            t.draws DESC,
            t.name ASC
    """)
    List<TeamEntity> findAllForStandings();

    @Modifying
    @Query("""
        UPDATE TeamEntity t 
        SET t.matchesPlayed = :matchesPlayed,
            t.wins = :wins,
            t.draws = :draws,
            t.losses = :losses,
            t.goalsScored = :goalsScored,
            t.goalsConceded = :goalsConceded,
            t.points = :points
        WHERE t.id = :id
    """)
    int updateTeamStats(
            @Param("id") Integer id,
            @Param("matchesPlayed") Integer matchesPlayed,
            @Param("wins") Integer wins,
            @Param("draws") Integer draws,
            @Param("losses") Integer losses,
            @Param("goalsScored") Integer goalsScored,
            @Param("goalsConceded") Integer goalsConceded,
            @Param("points") Integer points
    );

}
