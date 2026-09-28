package hu.martinez.matchsimulator.career.simulation.presimulation;

import hu.martinez.matchsimulator.career.lineup.Lineup;
import hu.martinez.matchsimulator.career.player.Player;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class PreSimulationTeamDataService {

    private final SimulatedPlayerMapper simulatedPlayerMapper;

    private final GoalKeeperSavingBonusService goalKeeperSavingBonusService;
    private final TeamAverageService teamAverageService;

    @Nonnull
    public PreSimulationTeamDataContainer getTeamData(
            @Nonnull String formation,
            @Nonnull List<Player> squadPlayerList,
            @Nonnull List<Lineup> starterPlayerList
    ) {

        List<SimulatedStarterPlayer> simulatedStarterPlayerList = new ArrayList<>();
        List<SimulatedBenchedPlayer> simulatedBenchedPlayerList = new ArrayList<>();

        for (var squadPlayer : squadPlayerList) {

            var isStarter = false;
            for (var starterPlayer : starterPlayerList) {
                if (Objects.equals(squadPlayer.id(), starterPlayer.playerId())) {
                    simulatedStarterPlayerList.add(simulatedPlayerMapper.map(squadPlayer, starterPlayer));
                    isStarter = true;
                    break;
                }
            }

            if (!isStarter) { // player goes to the bench
                simulatedBenchedPlayerList.add(simulatedPlayerMapper.map(squadPlayer));
            }
        }

        var goalKeeper = simulatedStarterPlayerList
                .stream()
                .filter(player -> "GK".equals(player.getPosition()))
                .findFirst()
                .orElseThrow();
        var defenderList = simulatedStarterPlayerList
                .stream()
                .filter(player -> List.of("RB", "RWB", "CB", "LB", "LWB").contains(player.getPosition()))
                .toList();
        var midfielderList = simulatedStarterPlayerList
                .stream()
                .filter(player -> List.of("CDM", "CM", "CAM", "RM", "LM").contains(player.getPosition()))
                .toList();
        var forwardList = simulatedStarterPlayerList
                .stream()
                .filter(player -> List.of("RW", "ST", "LW").contains(player.getPosition()))
                .toList();

        var teamAverage = teamAverageService.getStartingTeamAverage(
                forwardList,
                midfielderList,
                defenderList,
                goalKeeper
        );
        var goalKeeperSavingBonus = goalKeeperSavingBonusService.getBonus(goalKeeper);

       log.debug("formation: {}", formation);
       log.debug("forwardList.size: {}", forwardList.size());
       log.debug("midfielderList.size: {}", midfielderList.size());
       log.debug("defenderList.size: {}", defenderList.size());
       log.debug("benchSize: {}", simulatedBenchedPlayerList.size());
       log.debug("teamAverage: {}", teamAverage);
       log.debug("goalKeeperSavingBonus: {}", goalKeeperSavingBonus);

        return new PreSimulationTeamDataContainer(
                formation,
                forwardList,
                midfielderList,
                defenderList,
                goalKeeper,
                simulatedBenchedPlayerList,
                teamAverage,
                goalKeeperSavingBonus
        );
    }

}
