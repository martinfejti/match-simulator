package hu.martinez.matchsimulator.mainmenu;

import hu.martinez.matchsimulator.selector.season.SeasonService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mainmenu")
@RequiredArgsConstructor
public class MainMenuController {

    private final SeasonService seasonService;

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

}
