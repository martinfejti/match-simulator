package hu.martinez.matchsimulator.career.goal;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalMapper goalMapper;
    private final GoalRepository goalRepository;

    @Nonnull
    public List<Goal> getAllGoalsByPlayerId(@Nonnull Integer playerId) {
        return goalRepository.findByPlayerId(playerId)
                .stream()
                .map(goalMapper::map)
                .toList();
    }

    public void saveGoalsForFixture(@Nonnull List<CreateGoal> createGoalList) {
        goalRepository.saveAll(
                createGoalList
                        .stream()
                        .map(goalMapper::mapToEntity)
                        .toList()
        );
    }

    @Nonnull
    public Integer countGoalsByPlayerIdAndFixtureId(@Nonnull Integer playerId, @Nonnull Integer fixtureId) {
        return goalRepository.countByPlayerIdAndFixtureId(playerId, fixtureId);
    }

    @Nonnull
    public List<Goal> getAllGoalsByFixtureId(@Nonnull Integer fixtureId) {
        return goalRepository.findByFixtureId(fixtureId)
                .stream()
                .map(goalMapper::map)
                .toList();
    }

}
