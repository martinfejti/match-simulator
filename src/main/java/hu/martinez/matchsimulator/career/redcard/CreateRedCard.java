package hu.martinez.matchsimulator.career.redcard;

public record CreateRedCard(
        Integer fixtureId,
        Integer teamId,
        Integer playerId,
        Integer exclusionLength
) {
}
