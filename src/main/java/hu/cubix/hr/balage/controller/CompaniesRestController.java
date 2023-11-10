package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompaniesRestController {

    private final CompanyService companyService;

    @Autowired
    public CompaniesRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

//    @GetMapping
//    public List<CompanyDto> findAll() {
//        return new ArrayList<>(companyDtos.values());
//    }

    @GetMapping
    public List<CompanyDto> findAll(@Nullable @RequestParam(name = "full") Boolean full) {
        List<CompanyDto> ret = new ArrayList<>();
        List<CompanyDto> companyDtos = companyService.findAll();

        if (Boolean.TRUE.equals(full)) {
            ret.addAll(new ArrayList<>(companyDtos));
        } else {
            ret.addAll(
                    companyDtos.stream()
                            .map(CompaniesRestController::copyCompanyDto)
                            .toList()
            );
        }
        return ret;
    }

    @GetMapping(params = "salary")
    public List<CompanyDto> findByEmployeeSalaryGreaterThan(@RequestParam(name = "salary") Integer salary) {
        return companyService.findByEmployeeSalaryGreaterThan(salary);
    }

    @GetMapping(params = "employeeNumber")
    public List<CompanyDto> findByEmployeeNumber(@RequestParam(name = "employeeNumber") Integer employeeNumber) {
        return companyService.findByEmployeeNumber(employeeNumber);
    }

    @GetMapping("/{id}/averageSalary")
    public List<Double> averageSalaryByCompanyId(@PathVariable long id) {
        return companyService.averageSalaryByCompanyId(id);
    }

    private static CompanyDto copyCompanyDto(CompanyDto companyDto) {
        return new CompanyDto(
                companyDto.id(),
                companyDto.registrationNumber(),
                companyDto.name(),
                companyDto.address(),
                companyDto.companyForm(),
                Collections.emptyList());
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
        CompanyDto companyDto = companyService.findById(id);

        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }

        if (!Boolean.TRUE.equals(full)) {
            companyDto = copyCompanyDto(companyDto);
        }

        return ResponseEntity.ok(companyDto);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        CompanyDto savedCompanyDto = companyService.create(companyDto);
        if (savedCompanyDto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(savedCompanyDto);
    }

    @PutMapping("/{id}/updateCompany")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        if (companyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        CompanyDto updatedCompanyDto = companyService.update(companyDto);
        return ResponseEntity.ok(updatedCompanyDto);
    }

    @PutMapping("/{id}/addEmployee")
    public ResponseEntity<CompanyDto> addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        CompanyDto companyDto = companyService.addEmployee(id, employeeDto);
        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(companyDto);
    }

    @PutMapping("/{id}/refreshEmployees")
    public ResponseEntity<CompanyDto> refreshEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        CompanyDto companyDto = companyService.refreshEmployees(id, employeeDtos);
        if (companyDto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(companyDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        companyService.delete(id);
    }

    @DeleteMapping("/{companyId}/{employeeId}")
    public void delete(@PathVariable long companyId, @PathVariable long employeeId) {
        companyService.delete(companyId, employeeId);
    }
}