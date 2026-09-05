package hu.martinez.matchsimulator.selector.save;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SaveController {

    private final SaveService saveService;

    @GetMapping("selector/saves")
    @Nonnull
    public List<Save> getAllSaves() {
        return saveService.getAllSaves();
    }

    @GetMapping("selector/save-by-id")
    @Nonnull
    public Save getSaveById(@RequestParam Integer id) {
        return saveService.getSaveById(id);
    }

}
