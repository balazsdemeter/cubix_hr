package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class HrRestController {

    private Map<Long, EmployeeDto> employeeDtos = new HashMap<>();

    {
        employeeDtos.put(1L, new EmployeeDto(1L, "Teszt munka", 175000, LocalDateTime.now().minusMonths(5)));
    }

    @GetMapping
    public List<EmployeeDto> findAll() {
        return new ArrayList<>(employeeDtos.values());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findById(@PathVariable long id) {
        var employeeDto = employeeDtos.get(id);

        if (employeeDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping(params = "salary")
    public ResponseEntity<List<EmployeeDto>> filterBySalary(@RequestParam("salary") Integer salary) {

        List<EmployeeDto> employeeDtoList = new ArrayList<>(employeeDtos.values());

        List<EmployeeDto> filteredEmployees = employeeDtoList.stream()
                .filter(employeeDto -> employeeDto.getSalary() > salary)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredEmployees);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create(@RequestBody EmployeeDto employee) {
        if (employeeDtos.containsKey(employee.getId())) {
            return ResponseEntity.badRequest().build();
        }

        employeeDtos.put(employee.getId(), employee);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> update(@PathVariable long id, @RequestBody EmployeeDto employee) {
        if (!employeeDtos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        employeeDtos.put(id, employee);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        employeeDtos.remove(id);
    }
}