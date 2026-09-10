package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.SeasonService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/selector/save")
@RequiredArgsConstructor
@RestController
public class SaveController {

    private final SaveService saveService;
    private final SeasonService seasonService;

    @GetMapping("/get-all-saves")
    @Nonnull
    public List<Save> getAllSaves() {
        return saveService.getAllSaves();
    }

    @GetMapping("/get-save-by-id")
    @Nonnull
    public Save getSaveById(@RequestParam Integer id) {
        return saveService.getSaveById(id);
    }

    @PostMapping("/create-new-save")
    public Save createNewSave(
            @RequestParam String saveName,
            @RequestParam Integer seasonId
    ) {
        return saveService.saveNewSave(
                new CreateNewSave(
                        saveName,
                        seasonService.getSeasonById(seasonId)
                )
        );
    }

    @PostMapping("/open-save")
    public Save openSave(@RequestParam Integer saveId) {
        return saveService.openSave(saveId);
    }

}
