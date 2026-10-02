package hu.martinez.matchsimulator.career.simulation.store;

import hu.martinez.matchsimulator.career.goal.CreateGoal;
import hu.martinez.matchsimulator.career.goal.GoalService;
import hu.martinez.matchsimulator.career.injury.CreateInjury;
import hu.martinez.matchsimulator.career.injury.InjuryService;
import hu.martinez.matchsimulator.career.redcard.CreateRedCard;
import hu.martinez.matchsimulator.career.redcard.RedCardService;
import hu.martinez.matchsimulator.career.simulation.matchevent.chance.GoalsContainer;
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
    private final InjuryService injuryService;
    private final RedCardService redCardService;
    private final YellowCardService yellowCardService;

    @Transactional
    public void storeMatchResult(
            @Nonnull GoalsContainer goalsContainer,
            @Nonnull List<CreateYellowCard> yellowCardList,
            @Nonnull List<CreateRedCard> redCardList,
            @Nonnull List<CreateInjury> injuryList
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

        // red cards
        if (!redCardList.isEmpty()) {
            redCardService.saveRedCards(redCardList);
        }

        // injuries
        if (!injuryList.isEmpty()) {
            injuryService.saveAllInjuries(injuryList);
        }
    }

}
