package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;

public record PlayerAndChance(
        @Nonnull SimulatedStarterPlayer player,
        @Nonnull Boolean isBigChance
) {
}
