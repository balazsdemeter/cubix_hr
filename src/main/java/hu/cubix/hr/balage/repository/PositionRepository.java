package hu.cubix.hr.balage.repository;

import hu.cubix.hr.balage.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Position findByName(String name);
}