package hu.martinez.matchsimulator.career.fixture;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixtureRepository extends JpaRepository<FixtureEntity, Integer> {

    @Nonnull
    List<FixtureEntity> findByMatchWeekOrderByMatchNumberInWeekAsc(@Nonnull Integer matchWeek);

}
