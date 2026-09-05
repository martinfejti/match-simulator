package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.Season;
import jakarta.annotation.Nonnull;

public record Save(
        @Nonnull Integer id,
        @Nonnull String name,
        @Nonnull Season season
) {
}
