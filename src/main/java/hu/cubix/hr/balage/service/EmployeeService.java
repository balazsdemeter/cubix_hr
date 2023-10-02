package hu.cubix.hr.balage.service;

import java.time.LocalDateTime;

public interface EmployeeService {

    double getPayRaisePercent(LocalDateTime workStart);
}