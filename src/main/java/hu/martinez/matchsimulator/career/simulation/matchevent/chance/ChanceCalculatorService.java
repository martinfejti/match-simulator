package hu.martinez.matchsimulator.career.simulation.matchevent.chance;

import hu.martinez.matchsimulator.career.simulation.matchevent.PositionCheckerService;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationFixtureDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.PreSimulationTeamDataContainer;
import hu.martinez.matchsimulator.career.simulation.presimulation.SimulatedStarterPlayer;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Log4j2
@RequiredArgsConstructor
@Service
public class ChanceCalculatorService {

    // Egy átlagos csapat alapértékei egyenlő képességű ellenfél ellen
    private static final double BASE_SMALL_CHANCES = 6.5;
    private static final double BASE_BIG_CHANCES = 1.8;

    // Hazai pálya szorzók (Multiplikatív zárt modell)
    private static final double HOME_ADVANTAGE_MULT = 1.10; // +10%
    private static final double AWAY_HANDICAP_MULT = 0.90;  // -10%

    private final PositionCheckerService positionCheckerService;

    @Nonnull
    public ChanceContainer calculateChancesV2(
            @Nonnull PreSimulationFixtureDataContainer fixtureDataContainer,
            @Nonnull Double homeTeamAverage,
            @Nonnull Double awayTeamAverage
    ) {

        String homeFormation = fixtureDataContainer.homeTeamDataContainer().formation();
        String awayFormation = fixtureDataContainer.awayTeamDataContainer().formation();

        // 1. ALAP STÍLUS ÉS FORMÁCIÓS PROFIL
        FormationProfile homeProfile = getFormationProfile(homeFormation);
        FormationProfile awayProfile = getFormationProfile(awayFormation);

        double homeSmallBase = BASE_SMALL_CHANCES * homeProfile.smallChanceStyleMultiplier();
        double homeBigBase = BASE_BIG_CHANCES * homeProfile.bigChanceStyleMultiplier();

        double awaySmallBase = BASE_SMALL_CHANCES * awayProfile.smallChanceStyleMultiplier();
        double awayBigBase = BASE_BIG_CHANCES * awayProfile.bigChanceStyleMultiplier();

        // 2. HAZAI PÁLYA ELŐNY / VENDÉG HÁTRÁNY
        homeSmallBase *= HOME_ADVANTAGE_MULT;
        homeBigBase *= HOME_ADVANTAGE_MULT;

        awaySmallBase *= AWAY_HANDICAP_MULT;
        awayBigBase *= AWAY_HANDICAP_MULT;

        // 3. OVERALL KÜLÖNBSÉG (HATVÁNYOZOTT / RELATÍV ERŐFÖLÉNY) - LEGNAGYOBB SÚLY!
        double overallDiff = homeTeamAverage - awayTeamAverage;

        // Exponenciális skálázás
        double homeOverallMult = Math.pow(1.0 + (overallDiff / 50.0), 2.0);
        double awayOverallMult = 1.0 / homeOverallMult;

        // Capping Java 19 kompatibilis módon (0.15 és 2.5 között)
        homeOverallMult = clamp(homeOverallMult, 0.15, 2.5);
        awayOverallMult = clamp(awayOverallMult, 0.15, 2.5);

        homeSmallBase *= homeOverallMult;
        homeBigBase *= homeOverallMult;

        awaySmallBase *= awayOverallMult;
        awayBigBase *= awayOverallMult;

        // 4. ENERGIA ÉS POZÍCIÓ BÜNTETÉSEK
        double homePenalty = getPenaltiesForTeam(fixtureDataContainer.homeTeamDataContainer());
        double awayPenalty = getPenaltiesForTeam(fixtureDataContainer.awayTeamDataContainer());

        homeSmallBase *= homePenalty;
        homeBigBase *= homePenalty;

        awaySmallBase *= awayPenalty;
        awayBigBase *= awayPenalty;

        // 5. TAKTIKAI MÁTRIX (Felállások egymás elleni viszonya - Minden párosítás lefedve)
        TacticalDifferenceContainer homeTactical = getTacticalDifference(homeFormation, awayFormation);
        TacticalDifferenceContainer awayTactical = getTacticalDifference(awayFormation, homeFormation);

        homeSmallBase *= homeTactical.smallChanceMultiplier();
        homeBigBase *= homeTactical.bigChanceMultiplier();

        awaySmallBase *= awayTactical.smallChanceMultiplier();
        awayBigBase *= awayTactical.bigChanceMultiplier();

        // 6. SZERENCSE FAKTOR (+/- 15%)
        homeSmallBase *= ThreadLocalRandom.current().nextDouble(0.85, 1.15);
        homeBigBase *= ThreadLocalRandom.current().nextDouble(0.85, 1.15);

        awaySmallBase *= ThreadLocalRandom.current().nextDouble(0.85, 1.15);
        awayBigBase *= ThreadLocalRandom.current().nextDouble(0.85, 1.15);

        // Kerekítés standard Math.round-al (>= 0.5 felfelé)
        return new ChanceContainer(
                (int) Math.round(homeBigBase),
                (int) Math.round(homeSmallBase),
                (int) Math.round(awayBigBase),
                (int) Math.round(awaySmallBase)
        );
    }

    // Java 19 kompatibilis clamp segédmetódus
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    private record FormationProfile(double smallChanceStyleMultiplier, double bigChanceStyleMultiplier) {}

    private FormationProfile getFormationProfile(String formation) {
        return switch (formation) {
            case "4-3-3" -> new FormationProfile(1.15, 1.05);
            case "4-4-2" -> new FormationProfile(1.00, 1.00);
            case "4-2-3-1" -> new FormationProfile(1.05, 0.95);
            case "3-5-2" -> new FormationProfile(0.90, 1.15);
            case "5-3-2" -> new FormationProfile(0.75, 0.85);
            default -> new FormationProfile(1.00, 1.00);
        };
    }

    @Nonnull
    private Double getPenaltiesForTeam(@Nonnull PreSimulationTeamDataContainer teamDataContainer) {

        double multiplier = 1.0;

        for (var player : teamDataContainer.forwardList()) {
            multiplier *= getPenaltiesForPlayer(player);
        }
        for (var player : teamDataContainer.midfielderList()) {
            multiplier *= getPenaltiesForPlayer(player);
        }
        for (var player : teamDataContainer.defenderList()) {
            multiplier *= getPenaltiesForPlayer(player);
        }

        return Math.max(0.70, multiplier);
    }

    @Nonnull
    private Double getPenaltiesForPlayer(@Nonnull SimulatedStarterPlayer starterPlayer) {

        double multiplier = 1.0;

        // energy TODO bigger penalties are considerable and therefore the 0.7 min team cap is also questionable
        if (starterPlayer.getEnergy() < 30) {
            multiplier *= 0.90;
        } else if (starterPlayer.getEnergy() < 50) {
            multiplier *= 0.95;
        } else if (starterPlayer.getEnergy() < 70) {
            multiplier *= 0.98;
        }

        // position
        if (!starterPlayer.getPosition().equals(starterPlayer.getPrimaryPosition())) {
            if (positionCheckerService.isPlayingInSecondaryPosition(
                    starterPlayer.getPosition(), starterPlayer.getOtherPositions())) {
                multiplier *= 0.98; // player is in a secondary position
            } else if (positionCheckerService.isPlayingInRelatedPosition(
                    starterPlayer.getPosition(), starterPlayer.getPrimaryPosition())) {
                multiplier *= 0.95; // player is in a related position
            } else {
                multiplier *= 0.9; // player is in an unfamiliar position
            }
        }

        return multiplier;
    }

    @Nonnull
    private TacticalDifferenceContainer getTacticalDifference(@Nonnull String teamFormation, @Nonnull String opponentFormation) {
        // Ha azonos a két felállás, nincsen taktikai eltérés
        if (teamFormation.equals(opponentFormation)) {
            return new TacticalDifferenceContainer(1.0, 1.0);
        }

        // Mind a 20 lehetséges eltérő párosítás lefedése (5x5 mátrix minusz az azonosak)
        return switch (teamFormation + " vs " + opponentFormation) {
            // --- 4-3-3 a többiek ellen ---
            case "4-3-3 vs 4-4-2" -> new TacticalDifferenceContainer(0.95, 1.20);
            case "4-3-3 vs 4-2-3-1" -> new TacticalDifferenceContainer(1.15, 0.85);
            case "4-3-3 vs 3-5-2" -> new TacticalDifferenceContainer(0.90, 1.15);
            case "4-3-3 vs 5-3-2" -> new TacticalDifferenceContainer(1.45, 0.60);

            // --- 4-4-2 a többiek ellen ---
            case "4-4-2 vs 4-3-3" -> new TacticalDifferenceContainer(1.10, 0.90);
            case "4-4-2 vs 4-2-3-1" -> new TacticalDifferenceContainer(1.20, 0.80);
            case "4-4-2 vs 3-5-2" -> new TacticalDifferenceContainer(1.25, 0.85);
            case "4-4-2 vs 5-3-2" -> new TacticalDifferenceContainer(1.30, 0.50);

            // --- 4-2-3-1 a többiek ellen ---
            case "4-2-3-1 vs 4-3-3" -> new TacticalDifferenceContainer(0.95, 1.10);
            case "4-2-3-1 vs 4-4-2" -> new TacticalDifferenceContainer(0.90, 1.25);
            case "4-2-3-1 vs 3-5-2" -> new TacticalDifferenceContainer(1.05, 1.10);
            case "4-2-3-1 vs 5-3-2" -> new TacticalDifferenceContainer(1.20, 0.65);

            // --- 3-5-2 a többiek ellen ---
            case "3-5-2 vs 4-3-3" -> new TacticalDifferenceContainer(0.90, 1.25);
            case "3-5-2 vs 4-4-2" -> new TacticalDifferenceContainer(0.85, 1.30);
            case "3-5-2 vs 4-2-3-1" -> new TacticalDifferenceContainer(1.10, 0.95);
            case "3-5-2 vs 5-3-2" -> new TacticalDifferenceContainer(1.15, 0.55);

            // --- 5-3-2 a többiek ellen ---
            case "5-3-2 vs 4-3-3" -> new TacticalDifferenceContainer(0.85, 1.15);
            case "5-3-2 vs 4-4-2" -> new TacticalDifferenceContainer(0.80, 1.20);
            case "5-3-2 vs 4-2-3-1" -> new TacticalDifferenceContainer(0.90, 1.05);
            case "5-3-2 vs 3-5-2" -> new TacticalDifferenceContainer(0.85, 1.15);

            // Ismeretlen / nem definiált felállás esetén semleges szorzók
            default -> new TacticalDifferenceContainer(1.0, 1.0);
        };
    }

    private record TacticalDifferenceContainer(
            Double smallChanceMultiplier,
            Double bigChanceMultiplier
    ) {
    }

}