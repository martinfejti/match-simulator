package hu.martinez.matchsimulator.career.lineup;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "match_lineup")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LineupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fixture_id", nullable = false)
    private Integer fixtureId;

    @Column(name = "team_id", nullable = false)
    private Integer teamId;

    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @Column(name = "position", nullable = false)
    private String position;

}
