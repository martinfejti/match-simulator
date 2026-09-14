package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.SaveInitializerService;
import hu.martinez.matchsimulator.selector.season.Season;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class SaveService {

    private final SaveInitializerService saveInitializerService;
    private final SaveMapper saveMapper;
    private final SaveRepository saveRepository;

    @Nonnull
    public List<Save> getAllSaves() {
        return saveRepository.findAll()
                .stream()
                .map(saveMapper::map)
                .toList();
    }

    @Nonnull
    public Save getSaveById(@Nonnull Integer id) {
        return saveRepository.findById(id)
                .map(saveMapper::map)
                .orElseThrow();
    }

    @Nonnull
    public Save saveNewSave(@Nonnull CreateNewSave save) {

        var newSave = saveRepository.save(saveMapper.map(save));

        log.debug("saveNewGame - New save has been created: {}", newSave.getName());

        try {
            saveInitializerService.saveNewGame(save.name(), getConcattedSeasonNameForSaving(save.season()));
        } catch (RuntimeException e) {
            log.error("saveNewGame - Error while creating database copy or database switch", e);

            saveRepository.delete(newSave);

            throw e;
        }

        log.debug("saveNewGame - Database has been changed to a new season clone");

        return saveMapper.map(newSave);
    }

    @Nonnull
    public Save openSave(@Nonnull Integer saveId) {

        var saveToOpen = saveRepository.findById(saveId);

        if (saveToOpen.isEmpty()) {
            throw new IllegalStateException("Save does not exist!");
        }
        saveInitializerService.switchDatabase(saveToOpen.get().getName());

        return saveMapper.map(saveToOpen.get());
    }

    @Nonnull
    public void revertToSelectorDatabase() {
        saveInitializerService.switchToSelectorDatabase();
    }

    @Nonnull
    private String getConcattedSeasonNameForSaving(@Nonnull Season season) {
        return season.date().replace("-", "_")
                + "_"
                + season.league().replace(" ", "_").toLowerCase();
    }

}
