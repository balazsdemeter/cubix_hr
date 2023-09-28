package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl implements SalaryService {

    private final EmployeeService employeeService;

    @Autowired
    public SalaryServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void setSalary(Employee employee) {
        employee.setSalary(employeeService.getPayRaisePercent(employee));
    }
}