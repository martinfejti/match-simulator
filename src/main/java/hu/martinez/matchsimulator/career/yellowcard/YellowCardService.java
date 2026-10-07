package hu.martinez.matchsimulator.career.yellowcard;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class YellowCardService {

    private final YellowCardMapper yellowCardMapper;
    private final YellowCardRepository yellowCardRepository;

    @Nonnull
    public List<YellowCard> getAllYellowCardByPlayerId(@Nonnull Integer playerId) {
        return yellowCardRepository.findByPlayerId(playerId)
                .stream()
                .map(yellowCardMapper::map)
                .toList();
    }

    public void saveYellowCards(@Nonnull List<CreateYellowCard> createYellowCardList) {
        createYellowCardList
                .stream()
                .map(yellowCardMapper::mapToEntity)
                .forEach(yellowCardRepository::save);
    }

    @Nonnull
    public Boolean existsYellowCardByPlayerIdAndFixtureId(@Nonnull Integer playerId, @Nonnull Integer fixtureId) {
        return yellowCardRepository.existsByPlayerIdAndFixtureId(playerId, fixtureId);
    }

    @Nonnull
    public List<YellowCard> getAllYellowCardsByFixtureId(@Nonnull Integer fixtureId) {
        return yellowCardRepository.findByFixtureId(fixtureId)
                .stream()
                .map(yellowCardMapper::map)
                .toList();
    }

}
