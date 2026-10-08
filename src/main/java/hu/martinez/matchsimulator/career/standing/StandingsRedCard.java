package hu.martinez.matchsimulator.career.standing;

public record StandingsRedCard(
        Integer playerId,
        String playerName,
        String nationality,
        String teamName,
        Integer numberOfRedCards
) {
}
