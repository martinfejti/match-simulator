package hu.martinez.matchsimulator.career.player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "team_id")
    private Integer teamId;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "age")
    private Integer age;

    @Column(name = "primary_position")
    private String primaryPosition;

    @Column(name = "otherPositions")
    private String otherPositions;

    @Column(name = "preferred_foot")
    private String preferredFoot;

    @Column(name = "overall")
    private Integer overall;

    @Column(name = "big_chance_finishing")
    private Integer bigChanceFinishing;

    @Column(name = "small_chance_finishing")
    private Integer smallChanceFinishing;

    @Column(name = "energy")
    private Integer energy;

    @Column(name = "injured_for")
    private Integer injuredFor;

    @Column(name = "excluded_for")
    private Integer excludedFor;

    @Column(name = "matches_played")
    private Integer matchesPlayed;

    @Column(name = "number_of_goals")
    private Integer numberOfGoals;

    @Column(name = "clean_sheets")
    private Integer cleanSheets;

    @Column(name = "number_of_yellow_cards")
    private Integer numberOfYellowCards;

    @Column(name = "numberOfRedCards")
    private Integer numberOfRedCards;

}
