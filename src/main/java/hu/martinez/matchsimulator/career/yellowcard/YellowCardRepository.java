package hu.martinez.matchsimulator.career.yellowcard;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YellowCardRepository extends JpaRepository<YellowCardEntity, Integer> {

    @Nonnull
    List<YellowCardEntity> findByPlayerId(@Nonnull Integer playerId);

}
