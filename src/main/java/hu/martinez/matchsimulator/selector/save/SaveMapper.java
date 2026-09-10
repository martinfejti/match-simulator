package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.SeasonMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMapper {

    private final SeasonMapper seasonMapper;

    @Nonnull
    public Save map(@Nonnull SaveEntity entity) {
        return new Save(
                entity.getId(),
                entity.getName(),
                seasonMapper.map(entity.getSeason())
        );
    }

    @Nonnull
    public SaveEntity map(@Nonnull CreateNewSave save) {
        return new SaveEntity(null, save.name(), seasonMapper.map(save.season()));
    }
}
