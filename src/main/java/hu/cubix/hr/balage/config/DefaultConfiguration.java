package hu.cubix.hr.balage.config;

import hu.cubix.hr.balage.service.DefaultEmployeeServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {

    @Bean
    public DefaultEmployeeServiceImpl getEmployeeService() {
        return new DefaultEmployeeServiceImpl();
    }
}