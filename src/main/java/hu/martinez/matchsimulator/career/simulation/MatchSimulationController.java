package hu.martinez.matchsimulator.career.simulation;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/career/simulation")
@RequiredArgsConstructor
public class MatchSimulationController {

    private final MatchSimulationService matchSimulationService;

    @PostMapping("/simulate-match")
    public String simulateMatch(@RequestParam Integer fixtureId, @Nonnull RedirectAttributes redirectAttributes) {

        matchSimulationService.simulateMatch(fixtureId);

        redirectAttributes.addAttribute("fixtureId", fixtureId);

        return "redirect:/career/match-result/get-match-result";
    }

}
