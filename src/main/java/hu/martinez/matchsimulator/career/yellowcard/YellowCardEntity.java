package hu.martinez.matchsimulator.career.yellowcard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "yellow_card")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class YellowCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fixture_id")
    private Integer fixtureId;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "player_id")
    private Integer playerId;

}
