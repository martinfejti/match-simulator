package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.Season;
import jakarta.annotation.Nonnull;

public record CreateNewSave(
        @Nonnull String name,
        @Nonnull Season season
) {
}
