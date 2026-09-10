package hu.martinez.matchsimulator.selector.season;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "season")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SeasonEntity {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column
    private String date;

    @Column
    private String league;

    @Column
    private String flag;

}
