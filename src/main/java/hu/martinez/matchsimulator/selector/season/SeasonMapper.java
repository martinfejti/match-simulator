package hu.martinez.matchsimulator.selector.season;

import hu.martinez.matchsimulator.flag.FlagLoaderService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeasonMapper {

    private final FlagLoaderService flagLoaderService;

    @Nonnull
    public Season map(@Nonnull SeasonEntity entity) {
        return new Season(
                entity.getId(),
                entity.getDate(),
                entity.getLeague(),
                flagLoaderService.getFlagByCode(entity.getFlag())
        );
    }

}
