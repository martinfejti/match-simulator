package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChanceService {

    private final ChanceCalculatorService chanceCalculatorService;
    private final ChanceSimulatorService chanceSimulatorService;

    @Nonnull
    public GoalsContainer handleChances(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        // chances
        var chanceContainer = chanceCalculatorService.calculateChances(
                fixtureDataContainer.homeTeamDataContainer().teamAverage(),
                fixtureDataContainer.awayTeamDataContainer().teamAverage()
        );

        fixtureDataContainer.simulatedFixture().setHomeBigChances(chanceContainer.numberOfHomeBigChances());
        fixtureDataContainer.simulatedFixture().setHomeSmallChances(chanceContainer.numberOfHomeSmallChances());
        fixtureDataContainer.simulatedFixture().setAwayBigChances(chanceContainer.numberOfAwayBigChances());
        fixtureDataContainer.simulatedFixture().setAwaySmallChances(chanceContainer.numberOfAwaySmallChances());

        var goalsContainer = chanceSimulatorService.simulateChances(
                fixtureDataContainer.homeTeamDataContainer(),
                fixtureDataContainer.awayTeamDataContainer(),
                chanceContainer,
                fixtureDataContainer.simulatedFixture().getId()
        );

        fixtureDataContainer.simulatedFixture().setHomeScore(goalsContainer.homeTeamGoalList().size());
        fixtureDataContainer.simulatedFixture().setAwayScore(goalsContainer.awayTeamGoalList().size());

        return goalsContainer;
    }


}
