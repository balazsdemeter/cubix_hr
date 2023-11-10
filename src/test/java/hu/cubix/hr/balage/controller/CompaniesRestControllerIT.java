package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.service.CompanyService;
import hu.cubix.hr.balage.service.PositionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompaniesRestControllerIT {

    private final CompaniesRestController companiesRestController;

    private final CompanyService companyService;

    private final PositionService positionService;

    @Autowired
    public CompaniesRestControllerIT(CompaniesRestController companiesRestController,
                                     CompanyService companyService,
                                     PositionService positionService) {
        this.companiesRestController = companiesRestController;
        this.companyService = companyService;
        this.positionService = positionService;
    }

    @Test
    public void test_addEmployee() {
        CompanyDto companyDto = companyService.findAll().stream().findFirst().orElse(null);
        if (companyDto != null) {
            String name = "sdf";
            String positionName = positionService.getRandomPositionName();
            int salary = 500000;
            String companyName = companyDto.name();
            LocalDateTime workStart = LocalDateTime.now().minusMonths(6);

            companiesRestController.addEmployee(companyDto.id(), new EmployeeDto(0L, name, positionName, salary, companyName, workStart));


            CompanyDto updatedCompany = companyService.findById(companyDto.id());
            List<EmployeeDto> employees = updatedCompany.employees();
            EmployeeDto savedEmployee = employees.get(employees.size() - 1);

            assertThat(savedEmployee.getName()).isEqualTo(name);
            assertThat(savedEmployee.getPositionName()).isEqualTo(positionName);
            assertThat(savedEmployee.getSalary()).isEqualTo(salary);
            assertThat(savedEmployee.getCompanyName()).isEqualTo(companyName);
            assertThat(savedEmployee.getWorkStart()).isEqualTo(workStart);
        }
    }

    @Test
    public void test_refreshEmployees() {
        CompanyDto companyDto = companyService.findAll().stream().findFirst().orElse(null);
        if (companyDto != null) {
            String name = "sdf";
            String positionName = positionService.getRandomPositionName();
            int salary = 500000;
            String companyName = companyDto.name();
            LocalDateTime workStart = LocalDateTime.now().minusMonths(6);
            List<EmployeeDto> employees = new ArrayList<>();
            employees.add(new EmployeeDto(0L, name, positionName, salary, companyName, workStart));

            companiesRestController.refreshEmployees(companyDto.id(), employees);

            CompanyDto newCompanyDto = companyService.findById(companyDto.id());
            List<EmployeeDto> newEmployees = newCompanyDto.employees();

            assertThat(newEmployees.size()).isEqualTo(1);
            EmployeeDto newEmployee = newEmployees.get(0);
            assertThat(newEmployee.getPositionName()).isEqualTo(positionName);
            assertThat(newEmployee.getSalary()).isEqualTo(salary);
            assertThat(newEmployee.getPositionName()).isEqualTo(companyName);
            assertThat(newEmployee.getWorkStart()).isEqualTo(workStart);
        }
    }

    @Test
    public void test_deleteEmployee() {
        CompanyDto companyDto = companyService.findAll().stream().findFirst().orElse(null);

        if (companyDto != null) {
            List<EmployeeDto> employees = companyDto.employees();
            int beforeSize = employees.size();
            EmployeeDto employee = employees.stream().findAny().orElse(null);
            if (employee != null) {
                Long deletedEmployeeId = employee.getId();
                companiesRestController.delete(companyDto.id(), deletedEmployeeId);

                CompanyDto modifiedCompany = companyService.findById(companyDto.id());
                List<EmployeeDto> modifiedEmployees = modifiedCompany.employees();

                assertThat(modifiedEmployees.size()).isEqualTo(beforeSize - 1);
                assertThat(modifiedEmployees.stream().map(EmployeeDto::getId).toList().contains(deletedEmployeeId)).isEqualTo(false);
            }
        }
    }
}