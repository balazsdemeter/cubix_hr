package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class DefaultEmployeeServiceServiceImpl extends AbstractEmployeeService {

    @Autowired
    public DefaultEmployeeServiceServiceImpl(
            EmployeeRepository employeeRepository,
            CompanyService companyService,
            PositionRepository positionRepository) {
        super(employeeRepository, companyService, positionRepository);
    }

    @Override
    public double getPayRaisePercent(LocalDateTime workStart) {
        return 0.05;
    }
}