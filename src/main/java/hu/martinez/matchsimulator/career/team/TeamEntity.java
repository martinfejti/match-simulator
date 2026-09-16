package hu.martinez.matchsimulator.career.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "matches_played")
    private Integer matchesPlayed;

    @Column(name = "wins")
    private Integer wins;

    @Column(name = "draws")
    private Integer draws;

    @Column(name = "losses")
    private Integer losses;

    @Column(name = "goals_scored")
    private Integer goalsScored;

    @Column(name = "goals_conceded")
    private Integer goalsConceded;

    @Column(name = "points")
    private Integer points;

}