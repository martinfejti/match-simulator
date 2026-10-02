package hu.martinez.matchsimulator.career.injury;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InjuryRepository extends JpaRepository<InjuryEntity, Integer> {

    @Nonnull
    List<InjuryEntity> findByPlayerId(@Nonnull Integer playerId);

}
