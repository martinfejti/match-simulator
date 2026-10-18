package hu.martinez.matchsimulator.career.simulation.presimulation;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class GoalKeeperSavingBonusService {

    private static final Double BONUS_RATIO = 100.0;

    @Nonnull
    public Double getBonus(@Nonnull SimulatedStarterPlayer goalKeeper) {
        return goalKeeper.getOverall().doubleValue() / BONUS_RATIO;
    }

}
