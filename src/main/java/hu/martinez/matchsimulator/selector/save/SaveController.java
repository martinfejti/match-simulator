package hu.martinez.matchsimulator.selector.save;

import hu.martinez.matchsimulator.selector.season.SeasonService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/selector/save")
@RequiredArgsConstructor
public class SaveController {

    private final SaveService saveService;
    private final SeasonService seasonService;

    @PostMapping("/create-new-save")
    public String createNewSave(
            @RequestParam String saveName,
            @RequestParam Integer seasonId,
            @Nonnull RedirectAttributes redirectAttributes
    ) {

        var newSave = saveService.saveNewSave(
                new CreateNewSave(
                        saveName,
                        seasonService.getSeasonById(seasonId)
                )
        );

        redirectAttributes.addFlashAttribute("save", newSave);

        return "redirect:/career/menu/get-number-of-teams";
    }

    @GetMapping("/open-save")
    public String openSave(
            @RequestParam Integer saveId,
            @Nonnull RedirectAttributes redirectAttributes
    ) {

        var loadedSave = saveService.openSave(saveId);

        redirectAttributes.addFlashAttribute("save", loadedSave);

        return "redirect:/career/menu/get-number-of-teams";
    }

    @PostMapping("/delete-save")
    public String deleteSave(@RequestParam Integer saveId, @Nonnull Model model) {

        saveService.deleteSave(saveId);

        var reloadedSaveList = saveService.getAllSaves();

        model.addAttribute("saveList", reloadedSaveList);

        return "load_save";
    }

}
