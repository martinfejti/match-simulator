package hu.martinez.matchsimulator.career.matchpreview;

import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.Player;
import hu.martinez.matchsimulator.career.player.PlayerService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchPreviewService {

    private final LineupService lineupService;
    private final PlayerService playerService;

    @Nonnull
    public List<Player> getStartingPlayerList(@Nonnull Integer fixtureId, @Nonnull Integer teamId) {

        var startingPlayers = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, teamId);

        return startingPlayers
                .stream()
                .map(p -> playerService.getPlayerById(p.playerId()))
                .toList();
    }

}
