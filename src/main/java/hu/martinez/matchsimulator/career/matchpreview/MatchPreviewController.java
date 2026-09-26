package hu.martinez.matchsimulator.career.matchpreview;

import hu.martinez.matchsimulator.career.fixture.FixtureService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/career/match-preview")
@RequiredArgsConstructor
public class MatchPreviewController {

    private final FixtureService fixtureService;
    private final MatchPreviewService matchPreviewService;

    @GetMapping("/get-next-fixture")
    public String getNextFixture(@Nonnull Model model, @Nonnull @RequestParam Integer fixtureId) {

        var nextFixture = fixtureService.getFixtureById(fixtureId);

        model.addAttribute("fixture", nextFixture);

        var homeStartingPlayerList = matchPreviewService.getStartingPlayerList(
                nextFixture.id(),
                nextFixture.homeTeam().id()
        );
        var awayStartingPlayerList = matchPreviewService.getStartingPlayerList(
                nextFixture.id(),
                nextFixture.awayTeam().id()
        );

        model.addAttribute("homeStartingPlayerList", homeStartingPlayerList);
        model.addAttribute("awayStartingPlayerList", awayStartingPlayerList);

        return "match_preview";
    }

}
