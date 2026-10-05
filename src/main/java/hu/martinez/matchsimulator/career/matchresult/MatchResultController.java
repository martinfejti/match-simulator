package hu.martinez.matchsimulator.career.matchresult;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.injury.InjuryService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/career/match-result")
@RequiredArgsConstructor
public class MatchResultController {

    private final FixtureService fixtureService;
    private final MatchResultService matchResultService;
    private final InjuryService injuryService;

    @GetMapping("/get-match-result")
    public String getMatchResult(@Nonnull Integer fixtureId, @Nonnull Model model) {

        // TODO the match events need an own separate box between the score and the lineups
        // TODO team average as statistics in the last box
        // TODO add match result links to every possibility and remove the default underline
        // TODO handle team forms in the statistics
        // TODO handle the redirect calling of this endpoint from the match simulation controller!

        var fixture = fixtureService.getFixtureById(fixtureId);

        var homeTeamPlayerList = matchResultService.getMatchResultPlayerList(fixtureId, fixture.homeTeam().id());
        var awayTeamPlayerList = matchResultService.getMatchResultPlayerList(fixtureId, fixture.awayTeam().id());

        model.addAttribute("fixture", fixture);

        model.addAttribute("homeTeamPlayerList", homeTeamPlayerList);
        model.addAttribute("awayTeamPlayerList", awayTeamPlayerList);

        var homeInjuriesCount = injuryService.countInjuriesByTeamIdAndFixtureId(fixture.homeTeam().id(), fixtureId);
        var awayInjuriesCount = injuryService.countInjuriesByTeamIdAndFixtureId(fixture.awayTeam().id(), fixtureId);
        model.addAttribute("homeInjuriesCount", homeInjuriesCount);
        model.addAttribute("awayInjuriesCount", awayInjuriesCount);

        return "match_result";
    }

}
