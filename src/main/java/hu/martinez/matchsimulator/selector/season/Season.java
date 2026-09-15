package hu.martinez.matchsimulator.selector.season;

import jakarta.annotation.Nonnull;

public record Season(
        @Nonnull Integer id,
        @Nonnull String date,
        @Nonnull String league,
        @Nonnull String flag
) {
}
