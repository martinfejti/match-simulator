package hu.martinez.matchsimulator.career.simulation.presimulation;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamAverageService {

    @Nonnull
    public Double getStartingTeamAverage(
            @Nonnull List<SimulatedStarterPlayer> forwardList,
            @Nonnull List<SimulatedStarterPlayer> midfielderList,
            @Nonnull List<SimulatedStarterPlayer> defenderList,
            @Nonnull SimulatedStarterPlayer goalKeeper
    ) {
        return (
                getPositionAverage(forwardList)
                + getPositionAverage(midfielderList)
                + getPositionAverage(defenderList)
                + goalKeeper.getOverall().doubleValue()
        ) / 4;
    }

    @Nonnull
    private Double getPositionAverage(@Nonnull List<SimulatedStarterPlayer> starterPlayerList) {
        return starterPlayerList
                .stream()
                .map(SimulatedStarterPlayer::getOverall)
                .reduce(Integer::sum)
                .orElseThrow()
                .doubleValue()/ starterPlayerList.size();
    }

}
