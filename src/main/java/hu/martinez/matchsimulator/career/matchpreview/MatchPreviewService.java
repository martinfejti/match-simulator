package hu.martinez.matchsimulator.career.matchpreview;

import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchPreviewService {

    private final LineupService lineupService;
    private final PlayerService playerService;

    @Nonnull
    public List<MatchPreviewPlayer> getStartingPlayerList(@Nonnull Integer fixtureId, @Nonnull Integer teamId) {

        var startingPlayers = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, teamId);

        List<MatchPreviewPlayer> matchPreviewPlayerList = new ArrayList<>();
        for (var startingPlayer : startingPlayers) {
            var player = playerService.getPlayerById(startingPlayer.playerId());
            matchPreviewPlayerList.add(
                    new MatchPreviewPlayer(
                            player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                            player.shirtNumber(),
                            player.nationality(),
                            player.overall(),
                            startingPlayer.position()
                    )
            );
        }

        return matchPreviewPlayerList;
    }

}
