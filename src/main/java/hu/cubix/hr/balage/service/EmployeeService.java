package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {
    double getPayRaisePercent(LocalDateTime workStart);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee create(Employee employee);

    Employee update(Employee employee);

    Employee save(Employee employee);

    void delete(Long id);

    List<Employee> findByJob(String job);
    List<Employee> findByWorkStartDateBetween(LocalDateTime from, LocalDateTime to);
    List<Employee> findByNamePrefix(String name);
}