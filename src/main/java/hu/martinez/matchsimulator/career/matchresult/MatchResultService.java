package hu.martinez.matchsimulator.career.matchresult;

import hu.martinez.matchsimulator.career.goal.GoalService;
import hu.martinez.matchsimulator.career.injury.InjuryService;
import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.redcard.RedCardService;
import hu.martinez.matchsimulator.career.yellowcard.YellowCardService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchResultService {

    private final GoalService goalService;
    private final InjuryService injuryService;
    private final LineupService lineupService;
    private final PlayerService playerService;
    private final RedCardService redCardService;
    private final YellowCardService yellowCardService;

    @Nonnull
    public List<MatchResultPlayer> getMatchResultPlayerList(
            @Nonnull Integer fixtureId,
            @Nonnull Integer teamId
    ) {

        List<MatchResultPlayer> matchResultPlayerList = new ArrayList<>();
        var lineupList = lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, teamId);

        for (var lineup : lineupList) {

            var player = playerService.getPlayerById(lineup.playerId());

            matchResultPlayerList.add(
                    new MatchResultPlayer(
                            player.id(),
                            player.firstName().isEmpty() ? player.lastName() : player.firstName() + " " + player.lastName(),
                            player.shirtNumber(),
                            player.nationality(),
                            player.overall(),
                            lineup.position(),
                            goalService.countGoalsByPlayerIdAndFixtureId(player.id(), fixtureId),
                            yellowCardService.existsYellowCardByPlayerIdAndFixtureId(player.id(), fixtureId),
                            redCardService.getExclusionLengthByPlayerAndFixture(player.id(), fixtureId),
                            injuryService.getInjuryLengthByPlayerAndFixture(player.id(), fixtureId)
                    )
            );
        }

        return matchResultPlayerList;
    }

}
