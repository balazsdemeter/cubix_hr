package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Position;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position findByName(String positionName) {
        return positionRepository.findByName(positionName);
    }

    public String getRandomPositionName() {
        return positionRepository.findAll().stream().findFirst().map(Position::getName).orElse(null);
    }
}
