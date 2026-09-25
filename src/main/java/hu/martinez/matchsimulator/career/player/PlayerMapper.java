package hu.martinez.matchsimulator.career.player;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    @Nonnull
    public Player map(@Nonnull PlayerEntity entity) {
        return new Player(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getTeamId(),
                entity.getNationality(),
                entity.getAge(),
                entity.getPrimaryPosition(),
                entity.getOtherPositions(),
                entity.getPreferredFoot(),
                entity.getShirtNumber(),
                entity.getOverall(),
                entity.getBigChanceFinishing(),
                entity.getSmallChanceFinishing(),
                entity.getEnergy(),
                entity.getInjuredFor(),
                entity.getExcludedFor(),
                entity.getMatchesPlayed(),
                entity.getNumberOfGoals(),
                entity.getCleanSheets(),
                entity.getNumberOfYellowCards(),
                entity.getNumberOfRedCards()
        );
    }

    @Nonnull
    public Player mapToEntity(@Nonnull Player player) {
        return new Player(
                player.id(),
                player.firstName(),
                player.lastName(),
                player.teamId(),
                player.nationality(),
                player.age(),
                player.primaryPosition(),
                player.otherPositions(),
                player.preferredFoot(),
                player.shirtNumber(),
                player.overall(),
                player.bigChanceFinishing(),
                player.smallChanceFinishing(),
                player.energy(),
                player.injuredFor(),
                player.excludedFor(),
                player.matchesPlayed(),
                player.numberOfGoals(),
                player.cleanSheets(),
                player.numberOfYellowCards(),
                player.numberOfRedCards()
        );
    }

}
