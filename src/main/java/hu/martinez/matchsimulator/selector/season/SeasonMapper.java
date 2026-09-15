package hu.martinez.matchsimulator.selector.season;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeasonMapper {

    @Nonnull
    public Season map(@Nonnull SeasonEntity entity) {
        return new Season(
                entity.getId(),
                entity.getDate(),
                entity.getLeague(),
                entity.getFlag()
        );
    }

    @Nonnull
    public SeasonEntity map(@Nonnull Season season) {
        return new SeasonEntity(
                season.id(),
                season.date(),
                season.league(),
                season.flag()
        );
    }

}
