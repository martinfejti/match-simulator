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

    // TODO impressions so far:
    // the number of goals are starting to be realistic after the modification of the ridiculous chanceValue decrease
    // that part still needs some revision by the way
    // there are too few big chances (1 or 2 per team per match) and therefore the small chances have a big impact due to their sheer number
    // this is the reason why LM and RM or just midfielders in general scored more goals in the tests so far
    // midfielders have a greater chance to have small chance, and because there are 4 or 5 times more small chances, on the long run they score more goals
    // therefore we need to increase the number of chances, both of them, 2-6 big chances and 5-15 small chances should be the standard
    // and the score probabilities should be adjusted to them (mainly lowering the probabilities)
    // the goalkeeper saving value is ridiculously high and static (an 80 rated GK has 0.4 for every opponent chance!)
    // it seems we either have this high static value or the chance value decreasing as our two major tools to alter chance scoring probability

    private final ChanceCalculatorService chanceCalculatorService;
    private final ChanceSimulatorService2 chanceSimulatorService;

    @Nonnull
    public GoalsContainer handleChances(@Nonnull PreSimulationFixtureDataContainer fixtureDataContainer) {

        // chances
        var chanceContainer = chanceCalculatorService.calculateChancesV2(
                fixtureDataContainer,
                fixtureDataContainer.homeTeamDataContainer().teamAverage(),
                fixtureDataContainer.awayTeamDataContainer().teamAverage()
        );

        fixtureDataContainer.simulatedFixture().setHomeBigChances(chanceContainer.numberOfHomeBigChances());
        fixtureDataContainer.simulatedFixture().setHomeSmallChances(chanceContainer.numberOfHomeSmallChances());
        fixtureDataContainer.simulatedFixture().setAwayBigChances(chanceContainer.numberOfAwayBigChances());
        fixtureDataContainer.simulatedFixture().setAwaySmallChances(chanceContainer.numberOfAwaySmallChances());

        var goalsContainer = chanceSimulatorService.simulateChances(fixtureDataContainer, chanceContainer);

        fixtureDataContainer.simulatedFixture().setHomeScore(goalsContainer.homeTeamGoalList().size());
        fixtureDataContainer.simulatedFixture().setAwayScore(goalsContainer.awayTeamGoalList().size());

        return goalsContainer;
    }


}
