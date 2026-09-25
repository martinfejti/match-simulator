package hu.martinez.matchsimulator.career.lineup;

public record Lineup(
        Integer id,
        Integer fixtureId,
        Integer teamId,
        Integer playerId,
        String position
) {
}
