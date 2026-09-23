package hu.martinez.matchsimulator.career.player;

import org.springframework.data.jpa.repository.JpaRepository;
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
        ORDER BY p.name ASC
    """)
    List<PlayerEntity> findGoalkeepersByTeamId(@Param("teamId") Integer teamId);

    // 2. VÉDŐK (Defenders: CB, RB, LB, RWB, LWB)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('CB', 'RB', 'LB', 'RWB', 'LWB')
        ORDER BY p.name ASC
    """)
    List<PlayerEntity> findDefendersByTeamId(@Param("teamId") Integer teamId);

    // 3. KÖZÉPPÁLYÁSOK (Midfielders: CDM, CM, CAM, LM, RM)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('CDM', 'CM', 'CAM', 'LM', 'RM')
        ORDER BY p.name ASC
    """)
    List<PlayerEntity> findMidfieldersByTeamId(@Param("teamId") Integer teamId);

    // 4. TÁMADÓK (Forwards: ST, LW, RW)
    @Query("""
        SELECT p FROM PlayerEntity p 
        WHERE p.teamId = :teamId 
          AND p.primaryPosition IN ('ST', 'LW', 'RW')
        ORDER BY p.name ASC
    """)
    List<PlayerEntity> findForwardsByTeamId(@Param("teamId") Integer teamId);

}
