package hu.martinez.matchsimulator.career.schedule;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.team.TeamService;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/career/schedule")
public class ScheduleController {

    private final FixtureService fixtureService;
    private final TeamService teamService;

    @GetMapping("/get-schedule")
    public String getSchedule(@Nullable Integer teamId, @Nonnull Model model) {

        if (teamId == null) {
            teamId = 1;
        }

        var teamList = teamService.getAllTeams();
        model.addAttribute("teamList", teamList);

        var selectedTeam = teamService.getTeamById(teamId);
        model.addAttribute("selectedTeam", selectedTeam);

        var fixtureList = fixtureService.getFixturesByTeamId(teamId);
        model.addAttribute("fixtureList", fixtureList);

        return "schedule";
    }

}
