package hu.martinez.matchsimulator.career.simulation.postsimulation.teamstatistics;

import hu.martinez.matchsimulator.career.simulation.matchevent.MatchEventContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

@Service
public class TeamStatisticsService {

    public void handleTeamStatistics(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull MatchEventContainer matchEventContainer
    ) {

        handlePointsAndResults(fixtureDataContainer, matchEventContainer);
        handleGoals(fixtureDataContainer, matchEventContainer);
        handleMatchesPlayer(fixtureDataContainer, matchEventContainer);
    }

    private void handlePointsAndResults(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull MatchEventContainer matchEventContainer
    ) {

        if (matchEventContainer.goalsContainer().homeTeamGoalList().size()
                > matchEventContainer.goalsContainer().awayTeamGoalList().size()) {
            fixtureDataContainer.homeTeamDataContainer().team().setPoints(
                    fixtureDataContainer.homeTeamDataContainer().team().getPoints() + 3
            );
            fixtureDataContainer.homeTeamDataContainer().team().setWins(
                    fixtureDataContainer.homeTeamDataContainer().team().getWins() + 1
            );
            fixtureDataContainer.awayTeamDataContainer().team().setLosses(
                    fixtureDataContainer.awayTeamDataContainer().team().getLosses() + 1
            );
        } else if (matchEventContainer.goalsContainer().homeTeamGoalList().size()
                == matchEventContainer.goalsContainer().awayTeamGoalList().size()) {
            fixtureDataContainer.homeTeamDataContainer().team().setPoints(
                    fixtureDataContainer.homeTeamDataContainer().team().getPoints() + 1
            );
            fixtureDataContainer.awayTeamDataContainer().team().setPoints(
                    fixtureDataContainer.awayTeamDataContainer().team().getPoints() + 1
            );
            fixtureDataContainer.homeTeamDataContainer().team().setDraws(
                    fixtureDataContainer.homeTeamDataContainer().team().getDraws() + 1
            );
            fixtureDataContainer.awayTeamDataContainer().team().setDraws(
                    fixtureDataContainer.awayTeamDataContainer().team().getDraws() + 1
            );
        } else {
            fixtureDataContainer.awayTeamDataContainer().team().setPoints(
                    fixtureDataContainer.awayTeamDataContainer().team().getPoints() + 3
            );
            fixtureDataContainer.homeTeamDataContainer().team().setLosses(
                    fixtureDataContainer.homeTeamDataContainer().team().getLosses() + 1
            );
            fixtureDataContainer.awayTeamDataContainer().team().setWins(
                    fixtureDataContainer.awayTeamDataContainer().team().getWins() + 1
            );
        }
    }

    private void handleGoals(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull MatchEventContainer matchEventContainer
    ) {

        fixtureDataContainer.homeTeamDataContainer().team().setGoalsScored(
                fixtureDataContainer.homeTeamDataContainer().team().getGoalsScored()
                        + matchEventContainer.goalsContainer().homeTeamGoalList().size()
        );
        fixtureDataContainer.homeTeamDataContainer().team().setGoalsConceded(
                fixtureDataContainer.homeTeamDataContainer().team().getGoalsConceded()
                        + matchEventContainer.goalsContainer().awayTeamGoalList().size()
        );
        fixtureDataContainer.awayTeamDataContainer().team().setGoalsScored(
                fixtureDataContainer.awayTeamDataContainer().team().getGoalsScored()
                        + matchEventContainer.goalsContainer().awayTeamGoalList().size()
        );
        fixtureDataContainer.awayTeamDataContainer().team().setGoalsConceded(
                fixtureDataContainer.awayTeamDataContainer().team().getGoalsConceded()
                        + matchEventContainer.goalsContainer().homeTeamGoalList().size()
        );
    }

    private void handleMatchesPlayer(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull MatchEventContainer matchEventContainer
    ) {

        fixtureDataContainer.homeTeamDataContainer().team().setMatchesPlayed(
                fixtureDataContainer.homeTeamDataContainer().team().getMatchesPlayed() + 1
        );
        fixtureDataContainer.awayTeamDataContainer().team().setMatchesPlayed(
                fixtureDataContainer.awayTeamDataContainer().team().getMatchesPlayed() + 1
        );
    }

}
