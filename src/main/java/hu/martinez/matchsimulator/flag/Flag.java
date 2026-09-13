package hu.martinez.matchsimulator.flag;

import jakarta.annotation.Nonnull;

public record Flag(
        @Nonnull String code,
        // TODO: if i don't want to play with displaying image from bytearray, then i can just pass the filename to the JSP
        @Nonnull byte[] image
) {
}
