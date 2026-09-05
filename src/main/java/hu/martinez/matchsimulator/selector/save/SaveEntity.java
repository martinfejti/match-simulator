package hu.martinez.matchsimulator.selector.save;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "save")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "season_id")
    private Integer seasonId;

}
