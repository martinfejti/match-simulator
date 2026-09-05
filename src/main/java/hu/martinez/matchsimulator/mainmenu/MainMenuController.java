package hu.martinez.matchsimulator.mainmenu;

import hu.martinez.matchsimulator.saving.SaveInitializerService;
import hu.martinez.matchsimulator.team.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainMenuController {

    private final TeamRepository teamRepository;
    private final SaveInitializerService saveInitializerService;

    @GetMapping("newgame")
    public String startNewGame() {

        saveInitializerService.saveNewGame("my_first_own_save");

        var teamList = teamRepository.findAll();

        teamList.get(0).setName("Sevilla");

        teamRepository.save(teamList.get(0));

        return "Sikeresen létrehozott új mentés!";
    }

}
