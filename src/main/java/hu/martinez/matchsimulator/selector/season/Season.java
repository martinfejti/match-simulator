package hu.martinez.matchsimulator.selector.season;

import hu.martinez.matchsimulator.flag.Flag;
import jakarta.annotation.Nonnull;

public record Season(
        @Nonnull Integer id,
        @Nonnull String date,
        @Nonnull String league,
        @Nonnull Flag flag
) {
}
