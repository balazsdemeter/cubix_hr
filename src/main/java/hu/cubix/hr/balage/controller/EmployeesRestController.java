package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.dto.PageableEmployeeDto;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeesRestController {
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeesRestController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeMapper.employeesToDtos(employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
    }

    @GetMapping(params = "salary")
    public ResponseEntity<List<EmployeeDto>> findBySalary(@RequestParam("salary") Integer salary) {
        List<Employee> employees = employeeService.findAll();
        List<Employee> filteredEmployees = employees.stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeMapper.employeesToDtos(filteredEmployees));
    }

    @GetMapping(params = {"positionName", "pageNumber", "pageSize"})
    public ResponseEntity<PageableEmployeeDto> findByPositionName(@RequestParam("positionName") String positionName,
                                                                  @RequestParam("pageNumber") Integer pageNumber,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        Page<Employee> employees = employeeService.findByPositionName(positionName, pageNumber, pageSize);
        PageableEmployeeDto pageEmployeeDto;
        if (employees != null) {
            pageEmployeeDto = new PageableEmployeeDto(
                    employeeMapper.employeesToDtos(employees.stream().toList()),
                    employees.getTotalPages(),
                    employees.getTotalElements()
            );
        } else {
            pageEmployeeDto = new PageableEmployeeDto();
        }

        return ResponseEntity.ok(pageEmployeeDto);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<EmployeeDto>> findByDateBetween(@RequestParam("from") String from, @RequestParam("to") String to) {

        List<Employee> employees = employeeService.findByWorkStartDateBetween(
                LocalDateTime.parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(to, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(employeeMapper.employeesToDtos(employees));
    }

    @GetMapping(params = "namePrefix")
    public ResponseEntity<List<EmployeeDto>> findByNamePrefix(@RequestParam("namePrefix") String namePrefix) {
        List<Employee> employees = employeeService.findByNamePrefix(namePrefix);
        return ResponseEntity.ok(employeeMapper.employeesToDtos(employees));
    }

    @GetMapping("/raisePercentage")
    public ResponseEntity<Double> getRaisePercentage(@Valid @RequestBody EmployeeDto employeeDto) {
        double raisePercent = employeeService.getPayRaisePercent(employeeDto.getWorkStart());
        return ResponseEntity.ok(raisePercent);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.create(employeeMapper.dtoToEmployee(employeeDto));
        if (employee == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @Valid @RequestBody EmployeeDto employeeDto) {
        if (employeeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Employee employee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
        return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }
}