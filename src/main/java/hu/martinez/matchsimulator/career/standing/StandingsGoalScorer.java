package hu.martinez.matchsimulator.career.standing;

public record StandingsGoalScorer(
        Integer playerId,
        String playerName,
        String nationality,
        String teamName,
        Integer numberOfGoals
) {
}
