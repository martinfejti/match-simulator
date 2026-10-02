package hu.martinez.matchsimulator.career.simulation.presimulation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimulatedFixture {

        private Integer id;
        private Integer homeTeamId;
        private Integer awayTeamId;
        private String matchDate;
        private Integer homeScore;
        private Integer awayScore;
        private Integer homeBigChances;
        private Integer homeSmallChances;
        private Integer homeYellowCards;
        private Integer homeRedCards;
        private Integer awayBigChances;
        private Integer awaySmallChances;
        private Integer awayYellowCards;
        private Integer awayRedCards;
        private Boolean isFinished;

}
