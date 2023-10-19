package hu.cubix.hr.balage.service;

import java.time.LocalDateTime;

public class DefaultEmployeeServiceImpl extends AbstractEmployee {
    @Override
    public double getPayRaisePercent(LocalDateTime workStart) {
        return 0.05;
    }
}