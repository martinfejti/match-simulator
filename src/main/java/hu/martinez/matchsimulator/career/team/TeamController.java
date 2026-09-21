package hu.martinez.matchsimulator.career.team;

import hu.martinez.matchsimulator.career.player.PlayerService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/career/team")
public class TeamController {

    private final PlayerService playerService;
    private final TeamService teamService;

    @GetMapping("/get-team")
    public String getTeam(@Nullable Integer teamId, @Nonnull Model model) {

        if (teamId == null) {
            teamId = 1;
        }

        var teamList = teamService.getAllTeams();
        model.addAttribute("teamList", teamList);

        var selectedTeam = teamService.getTeamById(teamId);
        model.addAttribute("selectedTeam", selectedTeam);

        var goalkeeperList = playerService.getGoalkeepersByTeamId(teamId);
        model.addAttribute("goalkeeperList", goalkeeperList);

        var defenderList = playerService.getDefendersByTeamId(teamId);
        model.addAttribute("defenderList", defenderList);

        var midfielderList = playerService.getMidfieldersByTeamId(teamId);
        model.addAttribute("midfielderList", midfielderList);

        var forwardList = playerService.getForwardsByTeamId(teamId);
        model.addAttribute("forwardList", forwardList);

        return "team";
    }

}
