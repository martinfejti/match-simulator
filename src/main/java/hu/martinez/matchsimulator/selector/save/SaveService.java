package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.SeasonService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveService {

    private final SaveMapper saveMapper;
    private final SaveRepository saveRepository;

    private final SeasonService seasonService;

    @Nonnull
    public List<Save> getAllSaves() {
        return saveRepository.findAll()
                .stream()
                .map(entity -> saveMapper.map(entity, seasonService.getSeasonById(entity.getSeasonId())))
                .toList();
    }

    @Nonnull
    public Save getSaveById(@Nonnull Integer id) {
        return saveRepository.findById(id)
                .map(entity -> saveMapper.map(entity, seasonService.getSeasonById(entity.getSeasonId())))
                .orElseThrow();
    }

    @Nonnull
    public Integer saveNewSave(@Nonnull CreateNewSave save) {
        return saveRepository.save(saveMapper.map(save)).getId();
    }

}
