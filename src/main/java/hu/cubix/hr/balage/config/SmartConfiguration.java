package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.service.SmartEmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class SmartConfiguration {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public SmartConfiguration(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Bean
    public SmartEmployeeServiceImpl getEmployeeService() {
        return new SmartEmployeeServiceImpl(employeeRepository);
    }
}