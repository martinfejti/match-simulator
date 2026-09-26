package hu.martinez.matchsimulator.career;

import hu.martinez.matchsimulator.career.fixture.Fixture;
import hu.martinez.matchsimulator.career.fixture.FixtureService;
import hu.martinez.matchsimulator.career.schedule.ScheduleGeneratorService;
import hu.martinez.matchsimulator.career.team.TeamService;
import hu.martinez.matchsimulator.selector.save.SaveService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/career/menu")
@RequiredArgsConstructor
public class CareerMenuController {

    private final FixtureService fixtureService;
    private final SaveService saveService;
    private final ScheduleGeneratorService scheduleGeneratorService;
    private final TeamService teamService;

    @GetMapping
    public String displayCareerMenu(@Nonnull Model model) {

        var teamList = teamService.getAllTeams();
        var fixtureList = fixtureService.getNextMatchWeek();
        var numberOfFinishedFixtures = fixtureService.getNumberOfFinishedFixtures();
        var numberOfNotFinishedFixtures = fixtureService.getNumberOfNotFinishedFixtures();
        var nextFixture = fixtureService.getNextFixture();

        model.addAttribute("numberOfTeams", teamList.size());
        model.addAttribute("fixtureList", fixtureList);
        model.addAttribute("numberOfFinishedFixtures", numberOfFinishedFixtures);
        model.addAttribute("numberOfNotFinishedFixtures", numberOfNotFinishedFixtures);
        model.addAttribute("nextFixtureId", nextFixture.map(Fixture::id).orElse(null));

        return "careermenu";
    }

    @GetMapping("/go-back-to-main-menu")
    public String goBackToMainMenu() {

        saveService.revertToSelectorDatabase();

        return "main_menu";
    }

    @GetMapping("/start-new-season")
    public String startNewSeason(@Nonnull Model model) {

        // TODO FM: get and store the save into the copied database!

        var teamList = teamService.getAllTeams();
        var fixtureListToStore = scheduleGeneratorService.generateSchedule(teamList);
        fixtureService.storeFixtures(fixtureListToStore);

        var fixtureList = fixtureService.getFixturesByMatchWeekId(1);
        var numberOfFinishedFixtures = fixtureService.getNumberOfFinishedFixtures();
        var numberOfNotFinishedFixtures = fixtureService.getNumberOfNotFinishedFixtures();
        var nextFixture = fixtureService.getNextFixture();

        model.addAttribute("numberOfTeams", teamList.size());
        model.addAttribute("fixtureList", fixtureList);
        model.addAttribute("numberOfFinishedFixtures", numberOfFinishedFixtures);
        model.addAttribute("numberOfNotFinishedFixtures", numberOfNotFinishedFixtures);
        model.addAttribute("nextFixtureId", nextFixture.map(Fixture::id).orElse(null));

        return "careermenu";
    }

    @GetMapping("/open-season")
    public String openSeason(@Nonnull Model model) {

        var teamList = teamService.getAllTeams();
        var fixtureList = fixtureService.getNextMatchWeek();
        var numberOfFinishedFixtures = fixtureService.getNumberOfFinishedFixtures();
        var numberOfNotFinishedFixtures = fixtureService.getNumberOfNotFinishedFixtures();
        var nextFixture = fixtureService.getNextFixture();

        model.addAttribute("numberOfTeams", teamList.size());
        model.addAttribute("fixtureList", fixtureList);
        model.addAttribute("numberOfFinishedFixtures", numberOfFinishedFixtures);
        model.addAttribute("numberOfNotFinishedFixtures", numberOfNotFinishedFixtures);
        model.addAttribute("nextFixtureId", nextFixture.map(Fixture::id).orElse(null));

        return "careermenu";
    }

}
