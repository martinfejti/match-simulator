package hu.martinez.matchsimulator.career.simulation.matchevent;

import hu.martinez.matchsimulator.career.injury.CreateInjury;
import hu.martinez.matchsimulator.career.redcard.CreateRedCard;
import hu.martinez.matchsimulator.career.simulation.matchevent.chance.GoalsContainer;
import hu.martinez.matchsimulator.career.yellowcard.CreateYellowCard;

import java.util.List;

public record MatchEventContainer(
        GoalsContainer goalsContainer,
        List<CreateYellowCard> yellowCardList,
        List<CreateRedCard> redCardList,
        List<CreateInjury> injuryList
) {
}
