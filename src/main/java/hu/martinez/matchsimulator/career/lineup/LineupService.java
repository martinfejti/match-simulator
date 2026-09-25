package hu.martinez.matchsimulator.career.lineup;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LineupService {

    private final FixtureService fixtureService;
    private final LineupMapper lineupMapper;
    private final LineupRepository lineupRepository;

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
