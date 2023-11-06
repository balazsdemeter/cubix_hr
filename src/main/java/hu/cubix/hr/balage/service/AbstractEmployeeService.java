package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final PositionRepository positionRepository;

    protected AbstractEmployeeService(
            EmployeeRepository employeeRepository,
            CompanyService companyService,
            PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.positionRepository = positionRepository;
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
        employee.setCompany(companyService.findByName(employee.getCompany().getName()));
        employee.setPosition(positionRepository.findByName(employee.getPosition().getName()));
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> findByPositionName(String positionName, int pageNumber, int pageSize) {
        return employeeRepository.findEmployeesByPositionName(positionName, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public List<Employee> findByWorkStartDateBetween(LocalDateTime from, LocalDateTime to) {
        return employeeRepository.findEmployeesByWorkStartBetween(from, to);
    }

    @Override
    public List<Employee> findByNamePrefix(String namePrefix) {
        return employeeRepository.findByNameStartingWithIgnoreCase(namePrefix);
    }
}