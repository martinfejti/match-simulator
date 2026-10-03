package hu.martinez.matchsimulator.career.simulation.postsimulation.fixturefinisher;

import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedFixture;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FixtureFinisherService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void finishFixture(@Nonnull SimulatedFixture simulatedFixture) {

        simulatedFixture.setMatchDate(LocalDateTime.now().format(FORMATTER));
        simulatedFixture.setIsFinished(true);
    }

}
