package hu.martinez.matchsimulator.career.information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "information")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "save_name")
    private String saveName;

    @Column(name = "league_name")
    private String leagueName;

    @Column(name = "date")
    private String date;

    @Column(name = "country")
    private String country;

}
