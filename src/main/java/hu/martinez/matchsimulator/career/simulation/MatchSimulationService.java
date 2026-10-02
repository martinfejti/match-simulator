package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.career.simulation.matchevent.MatchEventService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.EnergyService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.ExclusionDecrementService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataService;
import hu.martinez.matchsimulator.career.simulation.store.MatchResultStoringService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MatchSimulationService {

    private final PreSimulationFixtureDataService preSimulationFixtureDataService;
    private final MatchEventService matchEventService;

    private final EnergyService energyService;
    private final ExclusionDecrementService exclusionDecrementService;

    private final MatchResultStoringService matchResultStoringService;

    public void simulateMatch(@Nonnull Integer fixtureId) {

        var fixtureData = preSimulationFixtureDataService.getFixtureData(fixtureId);

        var matchEventContainer = matchEventService.handleMatchEvents(fixtureData);

        // TODO put these into a post simulation service or find a proper place for them
        // exclusion decrement
        exclusionDecrementService.handleExclusionDecrement(fixtureData.homeTeamDataContainer().benchedPlayerList());
        exclusionDecrementService.handleExclusionDecrement(fixtureData.awayTeamDataContainer().benchedPlayerList());

        // fatigue
        energyService.handleFatigue(fixtureData.homeTeamDataContainer());
        energyService.handleFatigue(fixtureData.awayTeamDataContainer());

        // handle match results
        // TODO call the proper service

        // store results
        // matchResultStoringService.storeMatchResult(goalsContainer, yellowCardList, redCardList, injuryList);
    }

}
