package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;

public class DefaultEmployeeServiceImpl implements EmployeeService {

    @Override
    public int getPayRaisePercent(Employee employee) {
        var salary = employee.getSalary();
        return Double.valueOf(salary + (salary * 0.05)).intValue();
    }
}