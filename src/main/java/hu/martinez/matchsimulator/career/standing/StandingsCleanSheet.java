package hu.martinez.matchsimulator.career.standing;

public record StandingsCleanSheet(
        Integer playerId,
        String playerName,
        String nationality,
        String teamName,
        Integer numberOfCleanSheets
) {
}
