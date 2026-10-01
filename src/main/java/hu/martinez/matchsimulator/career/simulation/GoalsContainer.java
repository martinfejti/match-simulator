package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.career.goal.CreateGoal;

import java.util.List;

public record GoalsContainer(
        List<CreateGoal> homeTeamGoalList,
        List<CreateGoal> awayTeamGoalList
) {
}
