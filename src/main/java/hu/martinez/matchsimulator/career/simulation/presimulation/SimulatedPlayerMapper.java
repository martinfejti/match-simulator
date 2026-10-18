package hu.martinez.matchsimulator.career.simulation.presimulation;

import hu.martinez.matchsimulator.career.lineup.Lineup;
import hu.martinez.matchsimulator.career.player.Player;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class SimulatedPlayerMapper {

    // TODO chance value correction
    // big chance - CB - (0.6 x heading) + (0.4 finishing)
    // small chance - CB - (0.6 x shot power) + (0.4 x long shot)

    // big chance - fullback - (0.6 x finishing) + (0.4 x shot power)
    // small chance - fullback - (0.6 x long shot) + (0.4 x shot power)

    @Nonnull
    public SimulatedStarterPlayer map(@Nonnull Player squadPlayer, @Nonnull Lineup starterPlayer) {
        return new SimulatedStarterPlayer(
                squadPlayer.id(),
                squadPlayer.lastName(),
                squadPlayer.teamId(),
                starterPlayer.position(),
                squadPlayer.primaryPosition(),
                squadPlayer.otherPositions(),
                squadPlayer.overall(),
                squadPlayer.bigChanceFinishing().doubleValue() / 100.0,
                squadPlayer.smallChanceFinishing().doubleValue() / 100.0,
                squadPlayer.energy(),
                squadPlayer.injuredFor(),
                squadPlayer.excludedFor(),
                squadPlayer.matchesPlayed(),
                squadPlayer.numberOfGoals(),
                squadPlayer.cleanSheets(),
                squadPlayer.numberOfYellowCards(),
                squadPlayer.numberOfRedCards()
        );
    }

    @Nonnull
    public SimulatedBenchedPlayer map(@Nonnull Player squadPlayer) {
        return new SimulatedBenchedPlayer(
                squadPlayer.id(),
                squadPlayer.lastName(),
                squadPlayer.energy(),
                squadPlayer.injuredFor(),
                squadPlayer.excludedFor()
        );
    }

}
