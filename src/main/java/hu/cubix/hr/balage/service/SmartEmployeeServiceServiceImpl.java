package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.config.HrConfigurationProperties;
import hu.cubix.hr.balage.config.HrConfigurationProperties.Percent;
import hu.cubix.hr.balage.config.HrConfigurationProperties.Year;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Period;

public class SmartEmployeeServiceServiceImpl extends AbstractEmployeeService {
    private static final int MONTHS_PER_YEAR = 12;

//    private static final double YEAR_MAX = 10;
//    private static final double YEAR_MED = 5;
//    private static final double YEAR_MIN = 2.5;
//
//    private static final double PERCENT_MAX = 0.1;
//    private static final double PERCENT_MED = 0.05;
//    private static final double PERCENT_MIN = 0.02;
//    private static final double PERCENT_DEFAULT = 0.0;

    @Autowired
    private HrConfigurationProperties properties;

    public SmartEmployeeServiceServiceImpl(EmployeeRepository employeeRepository,
                                           EmployeeMapper employeeMapper,
                                           PositionService positionService) {
        super(employeeRepository, employeeMapper, positionService);
    }

    @Override
    public double getPayRaisePercent(LocalDateTime workStart) {
        var currentLocalDateTime = LocalDateTime.now();

        Period period = Period.between(workStart.toLocalDate(), currentLocalDateTime.toLocalDate());
        long totalMonths = period.toTotalMonths();

//        var raisePercent = PERCENT_DEFAULT;
//
//        if (years >= YEAR_MAX) {
//            raisePercent = PERCENT_MAX;
//        }
//
//        if (years >= YEAR_MED) {
//            raisePercent = PERCENT_MED;
//        }
//
//        if (YEAR_MED > years && years >= YEAR_MIN) {
//            raisePercent = PERCENT_MIN;
//        }

        Percent percent = properties.getPercent();
        double raisePercent = percent.getPercent_default();

        Year year = properties.getYear();

        double maxMonths = year.getYear_max() * MONTHS_PER_YEAR;
        if (totalMonths >= maxMonths) {
            raisePercent = percent.getPercent_year_max();
        }

        double medMonths = year.getYear_med() * MONTHS_PER_YEAR;
        if (maxMonths > totalMonths && totalMonths >= medMonths) {
            raisePercent = percent.getPercent_year_med();
        }

        double minMonths = year.getYear_min() * MONTHS_PER_YEAR;
        if (medMonths > totalMonths && totalMonths >= minMonths) {
            raisePercent = percent.getPercent_year_min();
        }

        return raisePercent;
    }
}