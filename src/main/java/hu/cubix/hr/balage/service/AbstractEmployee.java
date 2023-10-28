package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractEmployee implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    protected AbstractEmployee(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) {
            return null;
        }
        return save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }
        return save(employee);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findByJob(String job) {
        return employeeRepository.findEmployeesByJob(job);
    }

    @Override
    public List<Employee> findByWorkStartDateBetween(LocalDateTime from, LocalDateTime to) {
        return employeeRepository.findEmployeesByWorkStartBetween(from, to);
    }

    @Override
    public List<Employee> findByNamePrefix(String name) {
        return employeeRepository.findEmployeesByNameStartsWith(name.toUpperCase());
    }
}