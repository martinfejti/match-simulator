package hu.martinez.matchsimulator.career.simulation.store;

import hu.martinez.matchsimulator.career.goal.CreateGoal;
import hu.martinez.matchsimulator.career.goal.GoalService;
import hu.martinez.matchsimulator.career.simulation.GoalsContainer;
import hu.martinez.matchsimulator.career.yellowcard.CreateYellowCard;
import hu.martinez.matchsimulator.career.yellowcard.YellowCardService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MatchResultStoringService {

    private final GoalService goalService;
    private final YellowCardService yellowCardService;

    @Transactional
    public void storeMatchResult(
            @Nonnull GoalsContainer goalsContainer,
            @Nonnull List<CreateYellowCard> yellowCardList
    ) {

        // goals
        List<CreateGoal> createGoalList = new ArrayList<>();
        createGoalList.addAll(goalsContainer.homeTeamGoalList());
        createGoalList.addAll(goalsContainer.awayTeamGoalList());

        if (!createGoalList.isEmpty()) {
            goalService.saveGoalsForFixture(createGoalList);
        }

        // yellow cards
        if (!yellowCardList.isEmpty()) {
            yellowCardService.saveYellowCards(yellowCardList);
        }
    }

}
