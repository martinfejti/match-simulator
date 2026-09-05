package hu.martinez.matchsimulator.selector.save;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveRepository extends JpaRepository<SaveEntity, Integer> {
}
