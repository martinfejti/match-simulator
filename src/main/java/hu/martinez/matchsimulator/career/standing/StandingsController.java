package hu.martinez.matchsimulator.career.standing;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/career/standings")
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    @GetMapping
    public String getStandings(@Nonnull Model model) {

        var standingList = standingsService.getStandings();

        model.addAttribute("standingList", standingList);

        return "standings";
    }

}
