package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.lineup.LineupService;
import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.EnergyService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.InjuryService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MatchSimulationService {

    private final FixtureService fixtureService;
    private final LineupService lineupService;
    private final PlayerService playerService;

    private final ChanceCalculatorService chanceCalculatorService;
    private final ChanceSimulatorService chanceSimulatorService;
    private final PreSimulationTeamDataService preSimulationTeamDataService;

    private final EnergyService energyService;
    private final InjuryService injuryService;

    public void simulateMatch(@Nonnull Integer fixtureId) {

        var fixture = fixtureService.getFixtureById(fixtureId);

        if (fixture.isFinished()) {
            throw new IllegalStateException("Fixture was already simulated!");
        }

        // home team
        var homeTeamPlayerList = playerService.getPlayersByTeamId(fixture.homeTeam().id());
        var homeTeamStartingLineupList =
                lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.homeTeam().id());

        log.debug("Home Team - {}", fixture.homeTeam().name());
        var homeTeamDataContainer = preSimulationTeamDataService.getTeamData(
                fixture.homeFormation(),
                homeTeamPlayerList,
                homeTeamStartingLineupList
        );

        // away team
        var awayTeamPlayerList = playerService.getPlayersByTeamId(fixture.awayTeam().id());
        var awayTeamStartingLineupList =
                lineupService.getStartingPlayersByFixtureIdAndTeamId(fixtureId, fixture.awayTeam().id());

        log.debug("Away Team - {}", fixture.awayTeam().name());
        var awayTeamDataContainer = preSimulationTeamDataService.getTeamData(
                fixture.awayFormation(),
                awayTeamPlayerList,
                awayTeamStartingLineupList
        );

        // chances
        var chanceContainer = chanceCalculatorService.calculateChances( // TODO this could go into the chance simulator service!
                homeTeamDataContainer.teamAverage(),
                awayTeamDataContainer.teamAverage()
        );

        chanceSimulatorService.simulateChances(homeTeamDataContainer, awayTeamDataContainer, chanceContainer);

        // TODO these could go to a PostSimulationService or maybe not
        // fatigue
        energyService.handleFatigue(homeTeamDataContainer);
        energyService.handleFatigue(awayTeamDataContainer);

        // injuries
        injuryService.handleInjuries(homeTeamDataContainer);
        injuryService.handleInjuries(awayTeamDataContainer);
    }

}
