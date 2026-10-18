package hu.martinez.matchsimulator.career.simulation.matchevent;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PositionCheckerService {

    @Nonnull
    public Boolean isPlayingInSecondaryPosition(@Nonnull String currentPosition, @Nonnull String otherPositions) {

        if (otherPositions.isBlank()) {
            return false;
        }

        var otherPositionList = Arrays.stream(otherPositions.split(","))
                .map(String::trim)
                .toList();

        return otherPositionList.contains(currentPosition);
    }

    @Nonnull
    public Boolean isPlayingInRelatedPosition(@Nonnull String currentPosition, @Nonnull String primaryPosition) {
        return getZoneForPosition(currentPosition) == getZoneForPosition(primaryPosition);
    }

    @Nonnull
    private PositionFamily getZoneForPosition(@Nonnull String position) {
        return switch (position) {
            case "GK" -> PositionFamily.GOALKEEPER;
            case "CB" -> PositionFamily.DEFENDER_CENTRAL;
            case "RB", "RWB", "LB", "LWB" -> PositionFamily.DEFENDER_WIDE;
            case "CDM" -> PositionFamily.MIDFIELDER_DEFENSIVE;
            case "CM", "CAM" -> PositionFamily.MIDFIELDER_CENTRAL;
            case "RM", "RW", "LM", "LW" -> PositionFamily.WINGER;
            case "ST" -> PositionFamily.ATTACKER_CENTRAL;
            default -> throw new IllegalStateException("Unknown position!");
        };
    }

    private enum PositionFamily {
        GOALKEEPER,
        DEFENDER_CENTRAL,
        DEFENDER_WIDE,
        MIDFIELDER_DEFENSIVE,
        MIDFIELDER_CENTRAL,
        WINGER,
        ATTACKER_CENTRAL
    }

}
