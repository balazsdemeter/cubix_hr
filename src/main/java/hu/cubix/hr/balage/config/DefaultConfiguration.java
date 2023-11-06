package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import hu.cubix.hr.balage.service.CompanyService;
import hu.cubix.hr.balage.service.DefaultEmployeeServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {

    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final PositionRepository positionRepository;

    @Autowired
    public DefaultConfiguration(
            EmployeeRepository employeeRepository,
            CompanyService companyService,
            PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.positionRepository = positionRepository;
    }

    @Bean
    public DefaultEmployeeServiceServiceImpl getEmployeeService() {
        return new DefaultEmployeeServiceServiceImpl(employeeRepository, companyService, positionRepository);
    }
}