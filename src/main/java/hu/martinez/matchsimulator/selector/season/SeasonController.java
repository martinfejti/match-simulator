package hu.martinez.matchsimulator.selector.season;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/selector/season")
@RequiredArgsConstructor
@RestController
public class SeasonController {

    private final SeasonService seasonService;

    @GetMapping("/get-all-seasons")
    @Nonnull
    public List<Season> getAllSelectableSeasons() {
        return seasonService.getAllSelectableSeasons();
    }

}
