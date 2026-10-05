package hu.martinez.matchsimulator.career.simulation;

import hu.martinez.matchsimulator.selector.save.SaveService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/career/simulation")
@RequiredArgsConstructor
public class MatchSimulationController {

    private final MatchSimulationService matchSimulationService;

    private final SaveService saveService;

    @PostMapping("/simulate-match")
    public String simulateMatch(@RequestParam Integer fixtureId, @Nonnull RedirectAttributes redirectAttributes) {

        matchSimulationService.simulateMatch(fixtureId);

        // var loadedSave = saveService.openSave(saveId);
        // redirectAttributes.addFlashAttribute("save", loadedSave);

        return "redirect:/career/menu/open-season";
    }

}
