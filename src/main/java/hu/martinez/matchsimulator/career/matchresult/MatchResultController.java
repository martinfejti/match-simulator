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

        var matchEventList = matchResultService.getMatchResultEventList(fixtureId);
        model.addAttribute("matchEventList", matchEventList);

        var averageContainer = matchResultService.getStartingElevenAverages(fixtureId);
        model.addAttribute("averageContainer", averageContainer);

        var recentFormContainer = matchResultService.getRecentForms(fixtureId);
        model.addAttribute("recentFormContainer", recentFormContainer);

        return "match_result";
    }

}
