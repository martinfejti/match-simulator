package hu.martinez.matchsimulator.career;

import hu.martinez.matchsimulator.career.team.TeamService;
import hu.martinez.matchsimulator.selector.save.SaveService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("career/menu")
@RequiredArgsConstructor
public class CareerMenuController {

    private final SaveService saveService;
    private final TeamService teamService;

    @GetMapping("/go-back-to-main-menu")
    public String goBackToMainMenu() {

        saveService.revertToSelectorDatabase();

        return "main_menu";
    }

    // TODO FM: new endpoint as redirect destination, that triggers the population of the new database file

    @GetMapping("/get-number-of-teams")
    public String getNumberOfTeams(@Nonnull Model model) {

        var teamList = teamService.getAllTeams();

        model.addAttribute("numberOfTeams", teamList.size());

        return "careermenu";
    }

}
