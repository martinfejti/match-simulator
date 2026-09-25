package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.Team;
import lombok.Builder;

@Builder(toBuilder = true)
public record Fixture(
        Integer id,
        Integer matchWeek,
        Integer matchNumberInWeek,
        Team homeTeam,
        Team awayTeam,
        String matchDate,

        Integer homeScore,
        Integer awayScore,

        String homeFormation,
        Integer homeBigChances,
        Integer homeSmallChances,
        Integer homeYellowCards,
        Integer homeRedCards,

        String awayFormation,
        Integer awayBigChances,
        Integer awaySmallChances,
        Integer awayYellowCards,
        Integer awayRedCards,

        boolean isFinished
) {
}
