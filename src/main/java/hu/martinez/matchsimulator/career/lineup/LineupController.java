package hu.martinez.matchsimulator.career.lineup;

import hu.martinez.matchsimulator.career.player.PlayerService;
import hu.martinez.matchsimulator.career.team.TeamService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/career/lineup")
@RequiredArgsConstructor
public class LineupController {

    private final LineupService lineupService;
    private final PlayerService playerService;
    private final TeamService teamService;

    @GetMapping("/select-lineup")
    public String getLineup(
            @Nonnull Model model,
            @Nonnull @RequestParam Integer fixtureId,
            @Nonnull @RequestParam Integer teamId,
            @Nonnull @RequestParam Boolean isHomeTeam
    ) {

        var selectedTeam = teamService.getTeamById(teamId);
        model.addAttribute("selectedTeam", selectedTeam);

        var playerList = playerService.getPlayersByTeamId(selectedTeam.id());
        model.addAttribute("playerList", playerList);

        var goalkeeperList = playerService.getGoalkeepersByTeamId(selectedTeam.id());
        model.addAttribute("goalkeeperList", goalkeeperList);

        var defenderList = playerService.getDefendersByTeamId(selectedTeam.id());
        model.addAttribute("defenderList", defenderList);

        var midfielderList = playerService.getMidfieldersByTeamId(selectedTeam.id());
        model.addAttribute("midfielderList", midfielderList);

        var forwardList = playerService.getForwardsByTeamId(selectedTeam.id());
        model.addAttribute("forwardList", forwardList);

        model.addAttribute("fixtureId", fixtureId);
        model.addAttribute("isHomeTeam", isHomeTeam);

        return "lineup";
    }

    @PostMapping("/save-lineup")
    @ResponseBody
    public ResponseEntity<Map<String, String>> saveLineup(@RequestBody @Nonnull CreateLineup createLineup) {

        log.debug("saveLineup - formation: {}", createLineup.formation());
        createLineup.starterPlayerList()
                .forEach(player -> log.debug(
                        "saveLineup - Fixture ID: {}, Player ID: {}, Team ID: {}, Position: {}",
                        player.fixtureId(),
                        player.playerId(),
                        player.teamId(),
                        player.position()
                ));

        lineupService.saveLineup(createLineup);

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/career/menu");

        return ResponseEntity.ok(response);
    }

}
