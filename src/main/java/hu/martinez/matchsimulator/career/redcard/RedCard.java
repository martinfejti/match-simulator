package hu.martinez.matchsimulator.career.redcard;

public record RedCard(
        Integer id,
        Integer fixtureId,
        Integer teamId,
        Integer playerId,
        Integer exclusionLength
) {
}
