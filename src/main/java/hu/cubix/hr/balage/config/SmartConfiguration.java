package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.service.SmartEmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("smart")
public class SmartConfiguration {

    @Bean
    public SmartEmployeeServiceImpl getEmployeeService() {
        return new SmartEmployeeServiceImpl();
    }
}