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
        double payRaisePercent = employeeService.getPayRaisePercent(employee.getWorkStart());
        Integer oldSalary = employee.getSalary();
        int newSalary = Double.valueOf(oldSalary + (oldSalary * payRaisePercent)).intValue();
        employee.setSalary(newSalary);
    }
}