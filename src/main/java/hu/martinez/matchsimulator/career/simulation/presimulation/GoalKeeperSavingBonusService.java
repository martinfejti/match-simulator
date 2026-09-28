package hu.martinez.matchsimulator.career.simulation.presimulation;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class GoalKeeperSavingBonusService {

    private static final Double BONUS_RATIO = 200.0; // TODO maybe still a bit low --> higher here means smaller bonus

    @Nonnull
    public Double getBonus(@Nonnull SimulatedStarterPlayer goalKeeper) {
        return goalKeeper.getOverall().doubleValue() / BONUS_RATIO;
    }

}
