package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class DefaultEmployeeServiceServiceImpl extends AbstractEmployeeService {

    @Autowired
    public DefaultEmployeeServiceServiceImpl(EmployeeRepository employeeRepository,
                                             EmployeeMapper employeeMapper,
                                             PositionService positionService) {
        super(employeeRepository, employeeMapper, positionService);
    }

    @Override
    public double getPayRaisePercent(LocalDateTime workStart) {
        return 0.05;
    }
}