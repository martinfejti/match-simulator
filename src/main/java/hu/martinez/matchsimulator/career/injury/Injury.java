package hu.martinez.matchsimulator.career.injury;

public record Injury(
        Integer id,
        Integer fixtureId,
        Integer teamId,
        Integer playerId,
        Integer injuryLength
) {
}
