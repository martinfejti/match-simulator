package hu.martinez.matchsimulator.team;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeamController implements CommandLineRunner {

    private final TeamRepository teamRepository;

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) {
        System.out.println("====== ACTIVE DATASOURCE IS: " + dataSource.getClass().getName() + " ======");
    }

    @GetMapping("teams")
    @Nonnull
    public List<Team> getAllTeams() {
        //var teamList = teamRepository.findAll();

        //teamList.get(0).setName("Valenciaaaaaa");

        //teamRepository.save(teamList.get(0));

        //teamList = teamRepository.findAll();

        return teamRepository.findAll();
    }

}
