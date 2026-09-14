package hu.martinez.matchsimulator.selector.mainmenu;

import hu.martinez.matchsimulator.selector.save.SaveService;
import hu.martinez.matchsimulator.selector.season.SeasonService;
import hu.martinez.matchsimulator.team.TeamRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mainmenu")
@RequiredArgsConstructor
public class MainMenuController {

    private final SaveService saveService;
    private final SeasonService seasonService;
    private final TeamRepository teamRepository; // TODO temporal test only

    @GetMapping
    public String getMainMenu() {
        return "mainmenu";
    }

    @GetMapping("go-to-create-new-season")
    public String goToCreateNewSeason(@Nonnull Model model) {

        var seasonList = seasonService.getAllSelectableSeasons();

        model.addAttribute("seasonList", seasonList);

        return "newsave";
    }

    @GetMapping("go-to-load-season")
    public String goToLoadSeason(@Nonnull Model model) {

        var saveList = saveService.getAllSaves();

        model.addAttribute("saveList", saveList);

        return "loadsave";
    }

    @GetMapping("/open-save")
    public String openSave(@RequestParam Integer saveId, @Nonnull Model model) {

        var loadedSave = saveService.openSave(saveId);

        model.addAttribute("save", loadedSave);

        // TODO Read more about RedirectAttributes! that will transfer the save data into the new database via redirect
        return "redirect:/career/menu/get-number-of-teams";
    }

}
