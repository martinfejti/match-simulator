package hu.martinez.matchsimulator.career.schedule;

import jakarta.annotation.Nonnull;
import java.util.*;

public class ScheduleGeneratorService {

    public static final List<String> TEAM_LIST = List.of(
            "Team 1", "Equipo 2", "L'Équipe 3", "Squadra 4", "Csapat 5", "Mannschaft 6"
    );

    public record EnhancedMatchContainer(
            @Nonnull String homeTeam,
            @Nonnull String awayTeam,
            @Nonnull Integer matchNumberInWeek
    ) {}

    public record MatchWeekContainer(
            @Nonnull List<EnhancedMatchContainer> matchContainerList,
            @Nonnull Integer matchWeek
    ) {}

    public static void main(String[] args) {
        List<MatchWeekContainer> seasonSchedule = generateOptimalSchedule(TEAM_LIST);

        for (MatchWeekContainer week : seasonSchedule) {
            System.out.println("=== " + week.matchWeek() + ". FORDULÓ ===");
            for (EnhancedMatchContainer match : week.matchContainerList()) {
                System.out.printf("  %d. meccs: %s vs %s%n",
                        match.matchNumberInWeek(),
                        match.homeTeam(),
                        match.awayTeam());
            }
            System.out.println();
        }
    }

    /**
     * Berger-féle körforgásos algoritmus a matematikailag legkevesebb (1 db / félszezon)
     * duplázást tartalmazó sorsolás létrehozására.
     */
    public static List<MatchWeekContainer> generateOptimalSchedule(List<String> teams) {
        List<MatchWeekContainer> fullSchedule = new ArrayList<>();
        int numTeams = teams.size();
        int totalRounds = numTeams - 1;
        int matchesPerRound = numTeams / 2;

        List<Integer> teamIndexes = new ArrayList<>();
        for (int i = 0; i < numTeams; i++) {
            teamIndexes.add(i);
        }

        // 1. ŐSZI SZEZON GENERÁLÁSA
        for (int round = 0; round < totalRounds; round++) {
            List<EnhancedMatchContainer> rawRoundMatches = new ArrayList<>();

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
                rawRoundMatches.add(new EnhancedMatchContainer(
                        teams.get(homeIdx),
                        teams.get(awayIdx),
                        0
                ));
            }

            // --- MÓDOSÍTÁS: A fordulón belüli meccsek sorrendjének megkeverése ---
            Collections.shuffle(rawRoundMatches);

            // A kevert meccsek újra-sorszámozása (1..matchesPerRound)
            List<EnhancedMatchContainer> shuffledRoundMatches = new ArrayList<>();
            for (int i = 0; i < rawRoundMatches.size(); i++) {
                EnhancedMatchContainer m = rawRoundMatches.get(i);
                shuffledRoundMatches.add(new EnhancedMatchContainer(
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
            List<EnhancedMatchContainer> springMatches = new ArrayList<>();

            for (EnhancedMatchContainer match : autumnWeek.matchContainerList()) {
                springMatches.add(new EnhancedMatchContainer(
                        match.awayTeam(),
                        match.homeTeam(),
                        match.matchNumberInWeek()
                ));
            }

            fullSchedule.add(new MatchWeekContainer(springMatches, springRoundNumber));
        }

        return fullSchedule;
    }
}