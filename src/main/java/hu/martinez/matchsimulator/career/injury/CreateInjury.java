package hu.martinez.matchsimulator.career.injury;

public record CreateInjury(
        Integer fixtureId,
        Integer teamId,
        Integer playerId,
        Integer injuryLength
) {
}
