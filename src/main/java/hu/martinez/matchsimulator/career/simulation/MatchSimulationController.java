package hu.martinez.matchsimulator.career.simulation;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/career/simulation")
@RequiredArgsConstructor
public class MatchSimulationController {

    private final MatchSimulationService matchSimulationService;

    @PostMapping("/simulate-match")
    public void simulateMatch(@RequestParam Integer fixtureId, @Nonnull Model model) {

        matchSimulationService.simulateMatch(fixtureId);
    }

}
