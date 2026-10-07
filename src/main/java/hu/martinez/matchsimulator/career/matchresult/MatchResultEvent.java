package hu.martinez.matchsimulator.career.matchresult;

public record MatchResultEvent(
        String playerName,
        Boolean isHomeTeam,
        String eventType,
        Integer exclusionLength,
        Integer injuryLength
) {
}
