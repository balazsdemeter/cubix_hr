package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.model.Employee;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeService {
    double getPayRaisePercent(LocalDateTime workStart);

    List<EmployeeDto> findAll();

    EmployeeDto findById(Long id);

    EmployeeDto create(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    EmployeeDto save(EmployeeDto employeeDto);

    void delete(Long id);

    Page<EmployeeDto> findByPositionName(String positionName, int pageNumber, int pageSize);

    List<EmployeeDto> findByWorkStartDateBetween(LocalDateTime from, LocalDateTime to);

    List<EmployeeDto> findByNamePrefix(String namePrefix);

    List<EmployeeDto> findEmployeesByExample(EmployeeDto employeeDto);

    Employee createEmployeeEntity(EmployeeDto employeeDto);
}