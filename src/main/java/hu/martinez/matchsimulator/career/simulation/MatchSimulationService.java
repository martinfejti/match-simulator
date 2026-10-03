package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.career.simulation.matchevent.MatchEventService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.PostSimulationService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class MatchSimulationService {

    private final MatchEventService matchEventService;
    private final PostSimulationService postSimulationService;
    private final PreSimulationFixtureDataService preSimulationFixtureDataService;


    public void simulateMatch(@Nonnull Integer fixtureId) {

        var fixtureData = preSimulationFixtureDataService.getFixtureData(fixtureId);

        var matchEventContainer = matchEventService.handleMatchEvents(fixtureData);

        postSimulationService.handlePostSimulationTasks(fixtureData, matchEventContainer);

        // store results
        // matchResultStoringService.storeMatchResult(goalsContainer, yellowCardList, redCardList, injuryList);
    }

}
