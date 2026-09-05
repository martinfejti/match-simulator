package hu.martinez.matchsimulator.selector.season;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SeasonService {

    private final SeasonMapper seasonMapper;
    private final SeasonRepository seasonRepository;

    @Nonnull
    public List<Season> getAllSelectableSeasons() {
        return seasonRepository.findAll()
                .stream()
                .map(seasonMapper::map)
                .toList();
    }

    @Nonnull
    public Season getSeasonById(@Nonnull Integer id) {
        return seasonRepository.findById(id)
                .map(seasonMapper::map)
                .orElseThrow();
    }

}
