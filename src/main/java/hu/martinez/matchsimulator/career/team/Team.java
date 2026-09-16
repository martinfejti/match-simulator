package hu.martinez.matchsimulator.career.team;

import lombok.Builder;

@Builder(toBuilder = true)
public record Team(
        Integer id,
        String name,
        Integer matchesPlayed,
        Integer wins,
        Integer draws,
        Integer losses,
        Integer goalsScored,
        Integer goalsConceded,
        Integer points
) {
}
