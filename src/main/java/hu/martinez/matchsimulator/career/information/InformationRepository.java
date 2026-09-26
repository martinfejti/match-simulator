package hu.martinez.matchsimulator.career.information;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InformationRepository extends JpaRepository<InformationEntity, Integer> {

    @Nonnull
    Optional<InformationEntity> findFirstByOrderByIdAsc();

}
