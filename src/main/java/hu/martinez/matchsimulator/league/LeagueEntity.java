package hu.martinez.matchsimulator.league;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "league")
public class LeagueEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column
    private String name;

}
