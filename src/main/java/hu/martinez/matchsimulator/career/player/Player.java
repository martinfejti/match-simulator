package hu.martinez.matchsimulator.career.player;

public record Player(
        Integer id,
        String name,
        Integer teamId,
        String nationality,
        Integer age,
        String primaryPosition,
        String otherPositions,
        String preferredFoot,
        Integer shirtNumber,
        Integer overall,
        Integer bigChanceFinishing,
        Integer smallChanceFinishing,
        Integer energy,
        Integer injuredFor,
        Integer excludedFor,
        Integer matchesPlayed,
        Integer numberOfGoals,
        Integer cleanSheets,
        Integer numberOfYellowCards,
        Integer numberOfRedCards
) {
}
