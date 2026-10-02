package hu.martinez.matchsimulator.career.simulation.matchevent;

import hu.martinez.matchsimulator.career.simulation.matchevent.chance.ChanceService;
import hu.martinez.matchsimulator.career.simulation.matchevent.injury.SimulationInjuryService;
import hu.martinez.matchsimulator.career.simulation.matchevent.redcard.SimulationRedCardService;
import hu.martinez.matchsimulator.career.simulation.matchevent.yellowcard.SimulationYellowCardService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchEventService {

    private final ChanceService chanceService;
    private final SimulationInjuryService simulationInjuryService;
    private final SimulationRedCardService simulationRedCardService;
    private final SimulationYellowCardService simulationYellowCardService;

    @Nonnull
    public MatchEventContainer handleMatchEvents(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {
        return new MatchEventContainer(
                chanceService.handleChances(fixtureDataContainer),
                simulationYellowCardService.handleYellowCards(fixtureDataContainer),
                simulationRedCardService.handleRedCards(fixtureDataContainer),
                simulationInjuryService.handleInjuries(fixtureDataContainer)
        );
    }

}
