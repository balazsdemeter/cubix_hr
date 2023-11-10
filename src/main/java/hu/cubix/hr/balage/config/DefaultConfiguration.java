package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.service.DefaultEmployeeServiceServiceImpl;
import hu.cubix.hr.balage.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final PositionService positionService;

    @Autowired
    public DefaultConfiguration(EmployeeRepository employeeRepository,
                                EmployeeMapper employeeMapper,
                                PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.positionService = positionService;
    }

    @Bean
    public DefaultEmployeeServiceServiceImpl getEmployeeService() {
        return new DefaultEmployeeServiceServiceImpl(employeeRepository, employeeMapper, positionService);
    }
}