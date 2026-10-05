package hu.martinez.matchsimulator.career.injury;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InjuryService {

    private final InjuryMapper injuryMapper;
    private final InjuryRepository injuryRepository;

    @Nonnull
    public List<Injury> getAllInjuriesByPlayerId(@Nonnull Integer playerId) {
        return injuryRepository.findByPlayerId(playerId)
                .stream()
                .map(injuryMapper::map)
                .toList();
    }

    public void saveAllInjuries(@Nonnull List<CreateInjury> createInjuryList) {
        createInjuryList
                .stream()
                .map(injuryMapper::mapToEntity)
                .forEach(injuryRepository::save);
    }

    @Nonnull
    public Integer getInjuryLengthByPlayerAndFixture(@Nonnull Integer playerId, @Nonnull Integer fixtureId) {
        return injuryRepository.getInjuryLengthByPlayerAndFixture(playerId, fixtureId);
    }

    @Nonnull
    public Integer countInjuriesByTeamIdAndFixtureId(@Nonnull Integer teamId, @Nonnull Integer fixtureId) {
        return injuryRepository.countByTeamIdAndFixtureId(teamId, fixtureId);
    }

}
