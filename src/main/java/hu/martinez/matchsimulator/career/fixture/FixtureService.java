package hu.martinez.matchsimulator.career.fixture;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FixtureService {

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;

    @Nonnull
    public List<Fixture> getFixturesByMatchWeekId(@Nonnull Integer matchWeekId) {
        return fixtureRepository.findByMatchWeekOrderByMatchNumberInWeekAsc(matchWeekId)
                .stream()
                .map(fixtureMapper::map)
                .toList();
    }

}
