package hu.martinez.matchsimulator.selector.menubar;

import hu.martinez.matchsimulator.selector.save.SaveService;
import hu.martinez.matchsimulator.selector.season.SeasonService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/menubar")
@RequiredArgsConstructor
public class MenubarController {

    private final SaveService saveService;
    private final SeasonService seasonService;

    @GetMapping
    public String getMainMenu() {
        return "main_menu";
    }

    @GetMapping("go-to-create-new-season")
    public String goToCreateNewSeason(@Nonnull Model model) {

        var seasonList = seasonService.getAllSelectableSeasons();

        model.addAttribute("seasonList", seasonList);

        return "season_selector";
    }

    @GetMapping("go-to-load-season")
    public String goToLoadSeason(@Nonnull Model model) {

        var saveList = saveService.getAllSaves();

        model.addAttribute("saveList", saveList);

        return "load_save";
    }

    @GetMapping("/go-to-create-new-save")
    public String goToCreateNewSave(@RequestParam Integer seasonId, @Nonnull Model model) {

        var selectedSeason = seasonService.getSeasonById(seasonId);

        model.addAttribute("selectedSeason", selectedSeason);

        return "create_save";
    }

}
