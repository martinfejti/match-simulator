package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.Team;

public record CreateFixture(
        Integer matchWeek,
        Integer matchNumberInWeek,
        Team homeTeam,
        Team awayTeam
) {
}
