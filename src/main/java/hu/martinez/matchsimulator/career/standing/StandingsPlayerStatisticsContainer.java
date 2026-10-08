package hu.martinez.matchsimulator.career.standing;

import java.util.List;

public record StandingsPlayerStatisticsContainer(
        List<StandingsGoalScorer> goalScorerList,
        List<StandingsCleanSheet> cleanSheetList,
        List<StandingsYellowCard> yellowCardList,
        List<StandingsRedCard> redCardList
) {
}
