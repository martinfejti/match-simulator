package hu.martinez.matchsimulator.career.redcard;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RedCardRepository extends JpaRepository<RedCardEntity, Integer> {

    @Nonnull
    List<RedCardEntity> findByPlayerId(@Nonnull Integer playerId);

}
