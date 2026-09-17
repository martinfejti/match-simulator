package hu.martinez.matchsimulator.career.standing;

public record Standing(
        Integer teamId,
        String name,
        Integer matchesPlayed,
        Integer wins,
        Integer draws,
        Integer losses,
        Integer points,
        String goalsForAndAgainstFormatted,
        Integer goalDifference,
        String lastMatchResult
) {
}
