package hu.martinez.matchsimulator.career.player;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
        ORDER BY p.overall ASC
    """)
    List<PlayerEntity> findPlayersByTeamId(@Param("teamId") Integer teamId);

    // 1. KAPUSOK (Goalkeepers)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition = 'GK'
        ORDER BY p.lastName ASC
    """)
    List<PlayerEntity> findGoalkeepersByTeamId(@Param("teamId") Integer teamId);

    // 2. VÉDŐK (Defenders: CB, RB, LB, RWB, LWB)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('CB', 'RB', 'LB', 'RWB', 'LWB')
        ORDER BY p.lastName ASC
    """)
    List<PlayerEntity> findDefendersByTeamId(@Param("teamId") Integer teamId);

    // 3. KÖZÉPPÁLYÁSOK (Midfielders: CDM, CM, CAM, LM, RM)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('CDM', 'CM', 'CAM', 'LM', 'RM')
        ORDER BY p.lastName ASC
    """)
    List<PlayerEntity> findMidfieldersByTeamId(@Param("teamId") Integer teamId);

    // 4. TÁMADÓK (Forwards: ST, LW, RW)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('ST', 'LW', 'RW')
        ORDER BY p.lastName ASC
    """)
    List<PlayerEntity> findForwardsByTeamId(@Param("teamId") Integer teamId);

    @Nonnull
    @Query("SELECT p FROM PlayerEntity p WHERE p.numberOfGoals > 0 ORDER BY p.numberOfGoals DESC, p.lastName ASC LIMIT 15")
    List<PlayerEntity> findTop15GoalScorers();

    @Nonnull
    @Query("SELECT p FROM PlayerEntity p WHERE p.cleanSheets > 0 ORDER BY p.cleanSheets DESC, p.lastName ASC LIMIT 15")
    List<PlayerEntity> findTop15CleanSheets();

    @Nonnull
    @Query("SELECT p FROM PlayerEntity p WHERE p.numberOfYellowCards > 0 ORDER BY p.numberOfYellowCards DESC, p.lastName ASC LIMIT 15")
    List<PlayerEntity> findTop15YellowCards();

    @Nonnull
    @Query("SELECT p FROM PlayerEntity p WHERE p.numberOfRedCards > 0 ORDER BY p.numberOfRedCards DESC, p.lastName ASC LIMIT 15")
    List<PlayerEntity> findTop15RedCards();

    @Modifying
    @Query("""
        UPDATE PlayerEntity p 
        SET p.energy = :energy,
            p.injuredFor = :injuredFor,
            p.excludedFor = :excludedFor,
            p.matchesPlayed = :matchesPlayed,
            p.numberOfGoals = :numberOfGoals,
            p.cleanSheets = :cleanSheets,
            p.numberOfYellowCards = :numberOfYellowCards,
            p.numberOfRedCards = :numberOfRedCards
        WHERE p.id = :id
    """)
    int updateStarterPlayerPostMatchStats(
            @Param("id") Integer id,
            @Param("energy") Integer energy,
            @Param("injuredFor") Integer injuredFor,
            @Param("excludedFor") Integer excludedFor,
            @Param("matchesPlayed") Integer matchesPlayed,
            @Param("numberOfGoals") Integer numberOfGoals,
            @Param("cleanSheets") Integer cleanSheets,
            @Param("numberOfYellowCards") Integer numberOfYellowCards,
            @Param("numberOfRedCards") Integer numberOfRedCards
    );

    @Modifying
    @Query("""
        UPDATE PlayerEntity p 
        SET p.energy = :energy,
            p.injuredFor = :injuredFor,
            p.excludedFor = :excludedFor
        WHERE p.id = :id
    """)
    int updateBenchedPlayerPostMatchStats(
            @Param("id") Integer id,
            @Param("energy") Integer energy,
            @Param("injuredFor") Integer injuredFor,
            @Param("excludedFor") Integer excludedFor
    );

}
