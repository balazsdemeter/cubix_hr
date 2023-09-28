package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.config.HrConfigurationProperties;
import hu.cubix.hr.balage.config.HrConfigurationProperties.Percent;
import hu.cubix.hr.balage.config.HrConfigurationProperties.Year;
import hu.cubix.hr.balage.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class SmartEmployeeServiceImpl implements EmployeeService {

//    private static final double YEAR_10 = 10;
//    private static final double YEAR_5 = 5;
//    private static final double YEAR_2_5 = 2.5;
//
//    private static final double PERCENT_10 = 0.1;
//    private static final double PERCENT_05 = 0.05;
//    private static final double PERCENT_002 = 0.02;
//    private static final double PERCENT_DEFAULT = 0.0;

    @Autowired
    private HrConfigurationProperties properties;

    @Override
    public int getPayRaisePercent(Employee employee) {
        var workStart = employee.getWorkStart();
        var currentLocalDateTime = LocalDateTime.now();

        Period period = Period.between(workStart.toLocalDate(), currentLocalDateTime.toLocalDate());
        double months = (double) Math.round(period.get(ChronoUnit.MONTHS) / (double) 12 * 100) / 100;
        double years = period.getYears() + months;

        var salary = employee.getSalary();

//        var raisePercent = PERCENT_DEFAULT;
//
//        if (years >= YEAR_10) {
//            raisePercent = PERCENT_10;
//        }
//
//        if (years >= YEAR_5) {
//            raisePercent = PERCENT_05;
//        }
//
//        if (YEAR_5 > years && years >= YEAR_2_5) {
//            raisePercent = PERCENT_002;
//        }

        Percent percent = properties.getPercent();
        double raisePercent = percent.getPercent_default();

        Year year = properties.getYear();

        if (years >= year.getYear_1()) {
            raisePercent = percent.getPercent_year_1();
        }

        if (years >= year.getYear_2()) {
            raisePercent = percent.getPercent_year_2();
        }

        if (year.getYear_2() > years && years >= year.getYear_3()) {
            raisePercent = percent.getPercent_year_3();
        }

        return Double.valueOf(salary + (salary * raisePercent)).intValue();
    }
}
