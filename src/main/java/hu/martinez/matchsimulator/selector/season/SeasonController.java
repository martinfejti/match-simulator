package hu.martinez.matchsimulator.selector.season;

import hu.martinez.matchsimulator.selector.save.CreateNewSave;
import hu.martinez.matchsimulator.selector.save.SaveService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SeasonController {

    private final SaveService saveService;
    private final SeasonService seasonService;

    @GetMapping("selector/seasons")
    @Nonnull
    public List<Season> getAllSelectableSeasons() {
        return seasonService.getAllSelectableSeasons();
    }

    @PostMapping("selector/save-new-game")
    public Integer saveNewGame(@RequestParam Integer id) {
        return saveService.saveNewSave(
                new CreateNewSave(
                        "my_really_first_save",
                        seasonService.getSeasonById(id)
                )
        );
    }


}
