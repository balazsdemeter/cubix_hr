package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.dto.PageableEmployeeDto;
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

    @Autowired
    public EmployeesRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
        EmployeeDto employeeDto = employeeService.findById(id);

        if (employeeDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping(params = "salary")
    public ResponseEntity<List<EmployeeDto>> findBySalary(@RequestParam("salary") Integer salary) {
        List<EmployeeDto> employeeDtos = employeeService.findAll();
        List<EmployeeDto> filteredEmployeesDtos = employeeDtos.stream()
                .filter(employee -> employee.getSalary() > salary)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredEmployeesDtos);
    }

    @GetMapping(params = {"positionName", "pageNumber", "pageSize"})
    public ResponseEntity<PageableEmployeeDto> findByPositionName(@RequestParam("positionName") String positionName,
                                                                  @RequestParam("pageNumber") Integer pageNumber,
                                                                  @RequestParam("pageSize") Integer pageSize) {
        Page<EmployeeDto> page = employeeService.findByPositionName(positionName, pageNumber, pageSize);
        PageableEmployeeDto pageEmployeeDto;
        if (page != null) {
            pageEmployeeDto = new PageableEmployeeDto(page.getContent(), page.getTotalPages(), page.getTotalElements());
        } else {
            pageEmployeeDto = new PageableEmployeeDto();
        }

        return ResponseEntity.ok(pageEmployeeDto);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<List<EmployeeDto>> findByDateBetween(@RequestParam("from") String from, @RequestParam("to") String to) {

        List<EmployeeDto> employeeDtos = employeeService.findByWorkStartDateBetween(
                LocalDateTime.parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                LocalDateTime.parse(to, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping(params = "namePrefix")
    public ResponseEntity<List<EmployeeDto>> findByNamePrefix(@RequestParam("namePrefix") String namePrefix) {
        List<EmployeeDto> employeeDtos = employeeService.findByNamePrefix(namePrefix);
        return ResponseEntity.ok(employeeDtos);
    }

    @GetMapping("/raisePercentage")
    public ResponseEntity<Double> getRaisePercentage(@Valid @RequestBody EmployeeDto employeeDto) {
        double raisePercent = employeeService.getPayRaisePercent(employeeDto.getWorkStart());
        return ResponseEntity.ok(raisePercent);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto savedEmployeeDto = employeeService.create(employeeDto);
        if (savedEmployeeDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedEmployeeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @Valid @RequestBody EmployeeDto employeeDto) {
        if (employeeService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        EmployeeDto updatedEmployeeDto = employeeService.update(employeeDto);
        return ResponseEntity.ok(updatedEmployeeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeService.delete(id);
    }

    @GetMapping("/findByDynamically")
    public ResponseEntity<List<EmployeeDto>> findByDynamically(@RequestBody EmployeeDto employeeDto) {
        List<EmployeeDto> employeeDtos = employeeService.findEmployeesByExample(employeeDto);
        return ResponseEntity.ok(employeeDtos);
    }
}