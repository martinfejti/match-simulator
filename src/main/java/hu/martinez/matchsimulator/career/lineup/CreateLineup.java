package hu.martinez.matchsimulator.career.lineup;

import java.util.List;

public record CreateLineup(
        Integer fixtureId,
        Boolean isHomeTeam,
        String formation,
        List<StarterPlayer> starterPlayerList
) {

    public record StarterPlayer(
            Integer fixtureId,
            Integer teamId,
            Integer playerId,
            String position
    ) {
    }

}
