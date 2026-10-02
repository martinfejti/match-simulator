package hu.martinez.matchsimulator.career.simulation.presimulation;

import hu.martinez.matchsimulator.career.team.Team;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class SimulatedTeamMapper {

    @Nonnull
    public SimulatedTeam map(@Nonnull Team team) {
        return new SimulatedTeam(
                team.id(),
                team.name(),
                team.matchesPlayed(),
                team.wins(),
                team.draws(),
                team.losses(),
                team.goalsScored(),
                team.goalsConceded(),
                team.points()
        );
    }

}
