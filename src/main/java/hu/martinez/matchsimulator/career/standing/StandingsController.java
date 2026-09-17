package hu.martinez.matchsimulator.career.standing;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/career/standings")
@RequiredArgsConstructor
public class StandingsController {

    private final StandingsService standingsService;

    @GetMapping
    public String getStandings(@Nonnull Model model) {

        var standingList = standingsService.getStandings();

        model.addAttribute("standingList", standingList);

        // temporal goalscorer list
        List<Goalscorer> goalscorerList = new ArrayList<>();
        goalscorerList.add(new Goalscorer(1, "Sergio Agüero", "Manchester City", 2));
        goalscorerList.add(new Goalscorer(2, "Eden Hazard", "Chelsea", 1));
        goalscorerList.add(new Goalscorer(3, "Fernando Torres", "Chelsea", 1));
        goalscorerList.add(new Goalscorer(4, "Edin Dzeko", "Manchester City", 1));
        goalscorerList.add(new Goalscorer(5, "Luis Suarez", "Liverpool", 1));
        goalscorerList.add(new Goalscorer(6, "Lukas Podolski", "Arsenal", 1));
        goalscorerList.add(new Goalscorer(7, "Olivier Giroud", "Arsenal", 1));

        model.addAttribute("goalscorerList", goalscorerList);

        return "standings";
    }

}
