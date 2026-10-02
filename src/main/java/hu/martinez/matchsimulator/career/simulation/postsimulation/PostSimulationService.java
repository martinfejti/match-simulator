package hu.martinez.matchsimulator.career.simulation.postsimulation;

import hu.martinez.matchsimulator.career.simulation.postsimulation.energy.EnergyService;
import hu.martinez.matchsimulator.career.simulation.postsimulation.exclusion.ExclusionDecrementService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostSimulationService {

    private final EnergyService energyService;
    private final ExclusionDecrementService exclusionDecrementService;

    public void handlePostSimulationTasks(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        exclusionDecrementService.handleExclusionDecrement(fixtureDataContainer);
        energyService.handleFatigue(fixtureDataContainer);

        // TODO add point to teams and everything else that is left!
    }

}
