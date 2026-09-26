package hu.martinez.matchsimulator.career.lineup;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LineupService {

    private final FixtureService fixtureService;
    private final LineupMapper lineupMapper;
    private final LineupRepository lineupRepository;

    @Nonnull
    public List<Lineup> getStartingPlayersByFixtureIdAndTeamId(@Nonnull Integer fixtureId, @Nonnull Integer teamId) {
        return lineupRepository.findByFixtureIdAndTeamId(fixtureId, teamId)
                .stream()
                .map(lineupMapper::map)
                .toList();
    }

    @Transactional
    public void saveLineup(@Nonnull CreateLineup createLineup) {

        fixtureService.saveFormation(
                createLineup.isHomeTeam(),
                createLineup.fixtureId(),
                createLineup.formation()
        );

        createLineup.starterPlayerList()
                .forEach(
                        player -> lineupRepository.save(
                                lineupMapper.mapToEntity(player)
                        )
                );
    }

}
