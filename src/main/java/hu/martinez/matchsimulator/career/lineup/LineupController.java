package hu.martinez.matchsimulator.career.lineup;

import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.team.TeamService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/career/lineup")
@RequiredArgsConstructor
public class LineupController {

    private final PlayerService playerService;
    private final TeamService teamService;

    @GetMapping("/get-lineup")
    public String getLineup(@Nonnull Model model) {

        var selectedTeam = teamService.getTeamById(3); // TODO: only for testing
        model.addAttribute("team", selectedTeam);

        var playerList = playerService.getPlayersByTeamId(selectedTeam.id()); // TODO: only for testing
        model.addAttribute("playerList", playerList);

        var goalkeeperList = playerService.getGoalkeepersByTeamId(selectedTeam.id());
        model.addAttribute("goalkeeperList", goalkeeperList);

        var defenderList = playerService.getDefendersByTeamId(selectedTeam.id());
        model.addAttribute("defenderList", defenderList);

        var midfielderList = playerService.getMidfieldersByTeamId(selectedTeam.id());
        model.addAttribute("midfielderList", midfielderList);

        var forwardList = playerService.getForwardsByTeamId(selectedTeam.id());
        model.addAttribute("forwardList", forwardList);

        model.addAttribute("fixtureId", 3);

        return "lineup";
    }

}
