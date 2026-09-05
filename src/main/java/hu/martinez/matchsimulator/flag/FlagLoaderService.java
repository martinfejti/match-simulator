package hu.martinez.matchsimulator.flag;

import jakarta.annotation.Nonnull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FlagLoaderService {

    @Nonnull
    public Flag getFlagByCode(@Nonnull String code) {

        try {
            var file = new ClassPathResource("static/flag/" + code + ".png");

            return new Flag(file.getFilename(), file.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("Unexpected error during flag loading!", e);
        }
    }

}
