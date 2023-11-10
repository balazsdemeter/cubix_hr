package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.service.PositionService;
import hu.cubix.hr.balage.service.SmartEmployeeServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class SmartConfiguration {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final PositionService positionService;

    @Autowired
    public SmartConfiguration(EmployeeRepository employeeRepository,
                              EmployeeMapper employeeMapper,
                              PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.positionService = positionService;
    }

    @Bean
    public SmartEmployeeServiceServiceImpl getEmployeeService() {
        return new SmartEmployeeServiceServiceImpl(employeeRepository, employeeMapper, positionService);
    }
}