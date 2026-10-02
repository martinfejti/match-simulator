package hu.martinez.matchsimulator.career.simulation.presimulation;

import hu.martinez.matchsimulator.career.fixture.Fixture;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class SimulatedFixtureMapper {

    @Nonnull
    public SimulatedFixture map(@Nonnull Fixture fixture) {
        return new SimulatedFixture(
                fixture.id(),
                fixture.homeTeam().id(),
                fixture.awayTeam().id(),
                null,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                false
        );
    }

}
