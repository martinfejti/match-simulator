package hu.martinez.matchsimulator.career.matchresult;

import java.util.List;

public record MatchResultFormContainer(
        List<MatchResultForm> homeFormList,
        List<MatchResultForm> awayFormList
) {
}
