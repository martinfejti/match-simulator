package hu.martinez.matchsimulator.career.lineup;

import java.util.List;

public record CreateLineup(
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
