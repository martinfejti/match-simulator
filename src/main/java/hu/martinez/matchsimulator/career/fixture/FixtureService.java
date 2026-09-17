package hu.martinez.matchsimulator.career.fixture;

import hu.martinez.matchsimulator.career.team.Team;
import hu.martinez.matchsimulator.career.team.TeamMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class FixtureService {

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;
    private final TeamMapper teamMapper;

    @Nonnull
    public List<Fixture> getFixturesByMatchWeekId(@Nonnull Integer matchWeekId) {
        return fixtureRepository.findByMatchWeekOrderByMatchNumberInWeekAsc(matchWeekId)
                .stream()
                .map(fixtureMapper::map)
                .toList();
    }

    @Nonnull
    public void storeFixtures(@Nonnull List<CreateFixture> createFixtureList) {

        var fixtureListToStore = createFixtureList.stream()
                .map(fixtureMapper::mapToEntity)
                .toList();

        log.debug("storeFixture - fixtureListToStore.size: {}", fixtureListToStore.size());

        fixtureRepository.saveAll(fixtureListToStore);

        log.debug("storeFixture - Fixtures are created");
    }

    @Nonnull
    public List<Fixture> getNextMatchWeek() {

        var nextMatchWeekFixtures = fixtureRepository.findNextUnfinishedMatchWeekFixtures()
                .stream()
                .map(fixtureMapper::map)
                .toList();

        if (nextMatchWeekFixtures.isEmpty()) {
            return fixtureRepository.findFinalMatchWeekFixtures()
                    .stream()
                    .map(fixtureMapper::map)
                    .toList();
        }

        return nextMatchWeekFixtures;
    }

    @Nonnull
    public List<Fixture> getFixturesByTeamId(@Nonnull Integer teamId) {
        return fixtureRepository.findAllByTeamId(teamId)
                .stream()
                .map(fixtureMapper::map)
                .toList();
    }

    @Nonnull
    public Optional<Fixture> getLastFinishedFixtureForTeam(@Nonnull Team team) {

        var lastFixture = fixtureRepository.findLastFinishedFixtureForTeam(teamMapper.mapToEntity(team));

        return lastFixture.map(fixtureMapper::map);

    }

}
