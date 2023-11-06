package hu.cubix.hr.balage.mapper;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.model.Position;
import hu.cubix.hr.balage.model.enums.CompanyForm;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.boot.context.properties.bind.Name;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CompanyMapper {

    @Mapping(target = "id", source = "company.id")
    @Mapping(target = "registrationNumber", source = "company.registrationNumber")
    @Mapping(target = "name", source = "company.name")
    @Mapping(target = "address", source = "company.address")
    @Mapping(target = "companyForm", source = "company.companyForm.name")
    @Mapping(target = "employees", source = "company.employees", qualifiedByName = "mapEmployeesToDto")
    public abstract CompanyDto companyToDto(Company company);

    @Mapping(target = "id", source = "companyDto.id")
    @Mapping(target = "registrationNumber", source = "companyDto.registrationNumber")
    @Mapping(target = "name", source = "companyDto.name")
    @Mapping(target = "address", source = "companyDto.address")
    @Mapping(target = "companyForm", source = "companyDto.companyForm", qualifiedByName = "mapCompanyForm")
    @Mapping(target = "employees", source = "companyDto.employees", qualifiedByName = "mapDtoToEmployees")
    public abstract Company dtoToCompany(CompanyDto companyDto);

    @Named("mapEmployeesToDto")
    public List<EmployeeDto> mapEmployeesToDto(List<Employee> employees) {
        return employees.stream().map(this::employeeToDto).toList();
    }

    @Named("mapDtoToEmployees")
    public List<Employee> mapDtoToEmployees(List<EmployeeDto> employeeDtos) {
        return employeeDtos.stream().map(this::dtoToEmployee).toList();
    }

    @Named("mapCompanyForm")
    public CompanyForm mapCompanyForm(String companyForm) {
        return CompanyForm.findByName(companyForm);
    }

    public abstract List<CompanyDto> companiesToDtos(List<Company> companies);

    public abstract List<Company> dtosToCompanies(List<CompanyDto> companyDtos);

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "positionName", source = "employee.position.name")
    @Mapping(target = "salary", source = "employee.salary")
    @Mapping(target = "companyName", source = "employee.company.name")
    abstract EmployeeDto employeeToDto(Employee employee);

    @Mapping(target = "id", source = "employeeDto.id")
    @Mapping(target = "name", source = "employeeDto.name")
    @Mapping(target = "position.name", source = "employeeDto.positionName")
    @Mapping(target = "salary", source = "employeeDto.salary")
    @Mapping(target = "company.name", source = "employeeDto.companyName")
    abstract Employee dtoToEmployee(EmployeeDto employeeDto);
}
