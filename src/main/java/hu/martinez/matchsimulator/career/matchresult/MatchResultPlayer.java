package hu.martinez.matchsimulator.career.matchresult;

public record MatchResultPlayer(
        Integer id,
        String name,
        Integer shirtNumber,
        String nationality,
        Integer overall,
        String position,
        Integer numberOfGoals,
        Boolean hasYellowCard,
        Integer excludedFor,
        Integer injuredFor
) {
}
