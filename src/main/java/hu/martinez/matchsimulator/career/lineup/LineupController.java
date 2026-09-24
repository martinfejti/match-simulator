package hu.martinez.matchsimulator.career.lineup;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
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

    private final FixtureService fixtureService;
    private final PlayerService playerService;
    private final TeamService teamService;

    @GetMapping("/get-lineup")
    public String getLineup(@Nonnull Model model) {

        var selectedTeam = teamService.getTeamById(3); // TODO: only for testing
        model.addAttribute("selectedTeam", selectedTeam);

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

        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/career/menu");

        return ResponseEntity.ok(response);
    }

}
