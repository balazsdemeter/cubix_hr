package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.mapper.CompanyMapper;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.model.Company;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompaniesRestController {

    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public CompaniesRestController(CompanyService companyService, CompanyMapper companyMapper, EmployeeMapper employeeMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

//    @GetMapping
//    public List<CompanyDto> findAll() {
//        return new ArrayList<>(companyDtos.values());
//    }

    @GetMapping
    public List<CompanyDto> findAll(@Nullable @RequestParam(name = "full") Boolean full) {
        List<CompanyDto> ret = new ArrayList<>();
        Collection<CompanyDto> values = companyMapper.companiesToDtos(companyService.findAll());

        if (Boolean.TRUE.equals(full)) {
            ret.addAll(new ArrayList<>(values));
        } else {
            ret.addAll(
                    values.stream()
                            .map(CompaniesRestController::copyCompanyDto)
                            .toList()
            );
        }
        return ret;
    }

    private static CompanyDto copyCompanyDto(CompanyDto companyDto) {
        return new CompanyDto(
                companyDto.id(),
                companyDto.registrationNumber(),
                companyDto.name(),
                companyDto.address(),
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
        CompanyDto companyDto;
        Company company = companyService.findById(id);

        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        companyDto = companyMapper.companyToDto(company);
        if (!Boolean.TRUE.equals(full)) {
            companyDto = copyCompanyDto(companyDto);
        }

        return ResponseEntity.ok(companyDto);
    }

    @PostMapping
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyDto companyDto) {
        Company company = companyService.create(companyMapper.dtoToCompany(companyDto));
        if (company == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    @PutMapping("/{id}/updateCompany")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
        if (companyService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        Company company = companyService.update(companyMapper.dtoToCompany(companyDto));
        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    @PutMapping("/{id}/addEmployee")
    public ResponseEntity<CompanyDto> addEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
        Company company = companyService.addEmployee(id, employeeMapper.dtoToEmployee(employeeDto));
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(companyMapper.companyToDto(company));
    }

    @PutMapping("/{id}/refreshEmployees")
    public ResponseEntity<CompanyDto> refreshEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employeeDtos) {
        Company company = companyService.refreshEmployees(id, employeeMapper.dtosToEmployees(employeeDtos));
        if (company == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(companyMapper.companyToDto(company));
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