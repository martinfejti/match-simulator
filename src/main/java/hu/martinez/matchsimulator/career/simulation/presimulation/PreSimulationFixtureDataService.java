package hu.martinez.matchsimulator.career.simulation.presimulation;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class PreSimulationFixtureDataService {

    private final PreSimulationTeamDataService preSimulationTeamDataService;
    private final SimulatedFixtureMapper simulatedFixtureMapper;

    private final FixtureService fixtureService;
    private final LineupService lineupService;
    private final PlayerService playerService;

    @Nonnull
    public PreSimulationFixtureDataContainer getFixtureData(@Nonnull Integer fixtureId) {

        var fixture = fixtureService.getFixtureById(fixtureId);

        if (fixture.isFinished()) {
            throw new IllegalStateException("Fixture was already simulated!");
        }

        // home team
        log.debug("Home Team - {}", fixture.homeTeam().name());
        var homeTeamDataContainer = preSimulationTeamDataService.getTeamData(
                fixture.homeTeam(),
                fixture.homeFormation(),
                playerService.getPlayersByTeamId(fixture.homeTeam().id()),
                lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.homeTeam().id())
        );

        // away team
        log.debug("Away Team - {}", fixture.awayTeam().name());
        var awayTeamDataContainer = preSimulationTeamDataService.getTeamData(
                fixture.awayTeam(),
                fixture.awayFormation(),
                playerService.getPlayersByTeamId(fixture.awayTeam().id()),
                lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.awayTeam().id())
        );

        return new PreSimulationFixtureDataContainer(
                simulatedFixtureMapper.map(fixture),
                homeTeamDataContainer,
                awayTeamDataContainer
        );
    }

}
