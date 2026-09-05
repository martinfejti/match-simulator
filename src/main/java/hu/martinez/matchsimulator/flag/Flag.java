package hu.martinez.matchsimulator.flag;

import jakarta.annotation.Nonnull;

public record Flag(
        @Nonnull String code,
        @Nonnull byte[] image
) {
}
