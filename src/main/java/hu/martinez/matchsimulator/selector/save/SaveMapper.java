package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.Season;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMapper {

    @Nonnull
    public Save map(@Nonnull SaveEntity entity, @Nonnull Season season) {
        return new Save(
                entity.getId(),
                entity.getName(),
                season
        );
    }

    @Nonnull
    public SaveEntity map(@Nonnull CreateNewSave save) {
        return new SaveEntity(null, save.name(), save.season().id());
    }
}
