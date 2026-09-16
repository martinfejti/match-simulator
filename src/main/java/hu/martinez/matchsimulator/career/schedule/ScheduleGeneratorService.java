package hu.martinez.matchsimulator.career.schedule;

import hu.martinez.matchsimulator.career.fixture.CreateFixture;
import hu.martinez.matchsimulator.career.team.Team;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ScheduleGeneratorService {

    @Nonnull
    public List<CreateFixture> generateSchedule(@Nonnull List<Team> teamList) {

        var seasonSchedule = generateOptimalSchedule(teamList);
        var fixtureList = new ArrayList<CreateFixture>();

        for (var week : seasonSchedule) {
            for (var match : week.matchContainerList()) {
                fixtureList.add(
                        new CreateFixture(
                                week.matchWeek(),
                                match.matchNumberInWeek(),
                                match.homeTeam(),
                                match.awayTeam()
                        )
                );
            }
        }

        return fixtureList;
    }

    /**
     * Berger-féle körforgásos algoritmus a matematikailag legkevesebb (1 db / félszezon)
     * duplázást tartalmazó sorsolás létrehozására.
     */
    @Nonnull
    private List<MatchWeekContainer> generateOptimalSchedule(@Nonnull List<Team> teamList) {

        List<MatchWeekContainer> fullSchedule = new ArrayList<>();
        int numTeams = teamList.size();
        int totalRounds = numTeams - 1;
        int matchesPerRound = numTeams / 2;

        List<Integer> teamIndexes = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) {
            teamIndexes.add(i);
        }

        // 1. ŐSZI SZEZON GENERÁLÁSA
        for (int round = 0; round < totalRounds; round++) {
            List<MatchContainer> rawRoundMatches = new ArrayList<>();

            for (int match = 0; match < matchesPerRound; match++) {
                int homeIdx = teamIndexes.get(match);
                int awayIdx = teamIndexes.get(numTeams - 1 - match);

                if (match == 0) {
                    if (round % 2 == 1) {
                        int temp = homeIdx;
                        homeIdx = awayIdx;
                        awayIdx = temp;
                    }
                } else if (match % 2 == 1) {
                    int temp = homeIdx;
                    homeIdx = awayIdx;
                    awayIdx = temp;
                }

                // A matchNumberInWeek értéket ideiglenesen 0-val hozzuk létre
                rawRoundMatches.add(new MatchContainer(
                        teamList.get(homeIdx),
                        teamList.get(awayIdx),
                        0
                ));
            }

            // --- MÓDOSÍTÁS: A fordulón belüli meccsek sorrendjének megkeverése ---
            Collections.shuffle(rawRoundMatches);

            // A kevert meccsek újra-sorszámozása (1..matchesPerRound)
            List<MatchContainer> shuffledRoundMatches = new ArrayList<>();
            for (int i = 0; i < rawRoundMatches.size(); i++) {
                MatchContainer m = rawRoundMatches.get(i);
                shuffledRoundMatches.add(new MatchContainer(
                        m.homeTeam(),
                        m.awayTeam(),
                        i + 1 // Új, rendezett matchNumberInWeek
                ));
            }

            fullSchedule.add(new MatchWeekContainer(shuffledRoundMatches, round + 1));

            // Csapatok forgatása a Berger-módszer szerint
            teamIndexes.add(1, teamIndexes.remove(numTeams - 1));
        }

        // 2. TAVASZI SZEZON GENERÁLÁSA (Pályaválasztó cseréje, kevert sorrend megtartásával)
        for (int i = 0; i < totalRounds; i++) {
            MatchWeekContainer autumnWeek = fullSchedule.get(i);
            int springRoundNumber = autumnWeek.matchWeek() + totalRounds;
            List<MatchContainer> springMatches = new ArrayList<>();

            for (MatchContainer match : autumnWeek.matchContainerList()) {
                springMatches.add(new MatchContainer(
                        match.awayTeam(),
                        match.homeTeam(),
                        match.matchNumberInWeek()
                ));
            }

            fullSchedule.add(new MatchWeekContainer(springMatches, springRoundNumber));
        }

        return fullSchedule;
    }

    private record MatchContainer(
            @Nonnull Team homeTeam,
            @Nonnull Team awayTeam,
            @Nonnull Integer matchNumberInWeek
    ) {}

    private record MatchWeekContainer(
            @Nonnull List<MatchContainer> matchContainerList,
            @Nonnull Integer matchWeek
    ) {}

}