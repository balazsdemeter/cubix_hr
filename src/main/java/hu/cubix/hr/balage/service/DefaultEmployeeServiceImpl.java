package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class DefaultEmployeeServiceImpl extends AbstractEmployee {

    @Autowired
    public DefaultEmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super(employeeRepository);
    }

    @Override
    public double getPayRaisePercent(LocalDateTime workStart) {
        return 0.05;
    }
}