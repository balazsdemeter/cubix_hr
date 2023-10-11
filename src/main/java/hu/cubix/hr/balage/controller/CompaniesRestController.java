package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompaniesRestController {

    private Map<Long, CompanyDto> companyDtos = new TreeMap<>();

    {
        companyDtos.put(1L, new CompanyDto(
                        1L,
                        1234,
                        "Teszt1 munkahely",
                        "1111 Bp Teszt utca",
                        new ArrayList<>(Arrays.asList(
                                new EmployeeDto(1L, "Teszt Elek1", "Teszt1 munka", 175000, LocalDateTime.now().minusMonths(5)),
                                new EmployeeDto(2L, "Teszt Elek2", "Teszt2 munka", 150000, LocalDateTime.now().minusMonths(24))
                        ))
                )
        );
    }

//    @GetMapping
//    public List<CompanyDto> findAll() {
//        return new ArrayList<>(companyDtos.values());
//    }

    @GetMapping
    public List<CompanyDto> findAll(@Nullable @RequestParam(name = "full") Boolean full) {
        List<CompanyDto> ret = new ArrayList<>();
        Collection<CompanyDto> values = companyDtos.values();

        if (Boolean.TRUE.equals(full)) {
            ret.addAll(new ArrayList<>(values));
        } else {
            ret.addAll(
                    values.stream()
                            .map(companyDto -> new CompanyDto(
                                    companyDto.getId(),
                                    companyDto.getRegistrationNumber(),
                                    companyDto.getName(),
                                    companyDto.getAddress(),
                                    Collections.emptyList()))
                            .collect(Collectors.toList())
            );
        }
        return ret;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CompanyDto> findById(@PathVariable long id) {
//        var companyDto = companyDtos.get(id);
//
//        if (companyDto == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(companyDto);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@Nullable @RequestParam(name = "full") Boolean full, @PathVariable long id) {
        var ret = new CompanyDto();
        var companyDto = companyDtos.get(id);

        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }

        if (Boolean.TRUE.equals(full)) {
            ret = companyDto;
        } else {
            ret = new CompanyDto(
                    companyDto.getId(),
                    companyDto.getRegistrationNumber(),
                    companyDto.getName(),
                    companyDto.getAddress(),
                    Collections.emptyList()
            );
        }

        return ResponseEntity.ok(ret);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto company) {
        if (companyDtos.containsKey(company.getId())) {
            return ResponseEntity.badRequest().build();
        }

        companyDtos.put(company.getId(), company);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}/updateCompany")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto company) {
        if (!companyDtos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        companyDtos.put(id, company);
        return ResponseEntity.ok(company);
    }

    @PutMapping("/{id}/addEmployee")
    public ResponseEntity<CompanyDto> addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        if (!companyDtos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companyDtos.get(id);
        companyDto.getEmployees().add(employeeDto);
        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}/refreshEmployees")
    public ResponseEntity<CompanyDto> refreshEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        if (!companyDtos.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        CompanyDto companyDto = companyDtos.get(id);
        List<EmployeeDto> employees = companyDto.getEmployees();
        employees.clear();
        employees.addAll(employeeDtos);
        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyDtos.remove(id);
    }

    @DeleteMapping("/{companyId}/{employeeId}")
    public void delete(@PathVariable long companyId, @PathVariable long employeeId) {
        if (companyDtos.containsKey(companyId)) {
            CompanyDto companyDto = companyDtos.get(companyId);
            List<EmployeeDto> employees = companyDto.getEmployees();

            employees.stream()
                    .filter(employeeDto -> employeeDto.getId() == employeeId)
                    .findFirst()
                    .ifPresent(employees::remove);
        }
    }
}