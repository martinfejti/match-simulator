package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.TeamEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fixture")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FixtureEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "match_week")
    private Integer matchWeek;

    @Column(name = "match_number_in_week")
    private Integer matchNumberInWeek;

    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private TeamEntity homeTeam;

    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private TeamEntity awayTeam;

    @Column(name = "match_date")
    private String matchDate;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @Column(name = "home_big_chances")
    private Integer homeBigChances;

    @Column(name = "home_small_chances")
    private Integer homeSmallChances;

    @Column(name = "home_yellow_cards")
    private Integer homeYellowCards;

    @Column(name = "home_red_cards")
    private Integer homeRedCards;

    @Column(name = "away_big_chances")
    private Integer awayBigChances;

    @Column(name = "away_small_chances")
    private Integer awaySmallChances;

    @Column(name = "away_yellow_cards")
    private Integer awayYellowCards;

    @Column(name = "away_red_cards")
    private Integer awayRedCards;

    @Column(name = "is_finished")
    private boolean isFinished;

}
