package hu.martinez.matchsimulator.career.team;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
