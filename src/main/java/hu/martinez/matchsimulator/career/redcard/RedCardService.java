package hu.martinez.matchsimulator.career.redcard;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RedCardService {

    private final RedCardMapper redCardMapper;
    private final RedCardRepository redCardRepository;

    @Nonnull
    public List<RedCard> getAllRedCardsByPlayerId(@Nonnull Integer playerId) {
        return redCardRepository.findByPlayerId(playerId)
                .stream()
                .map(redCardMapper::map)
                .toList();
    }

    public void saveRedCards(@Nonnull List<CreateRedCard> createRedCardList) {
        createRedCardList
                .stream()
                .map(redCardMapper::mapToEntity)
                .forEach(redCardRepository::save);
    }

    @Nonnull
    public Integer getExclusionLengthByPlayerAndFixture(@Nonnull Integer playerId, @Nonnull Integer fixtureId) {
        return redCardRepository.getExclusionLengthByPlayerAndFixture(playerId, fixtureId);
    }

    @Nonnull
    public List<RedCard> getAllRedCardsByFixtureId(@Nonnull Integer fixtureId) {
        return redCardRepository.findByFixtureId(fixtureId)
                .stream()
                .map(redCardMapper::map)
                .toList();
    }

}
