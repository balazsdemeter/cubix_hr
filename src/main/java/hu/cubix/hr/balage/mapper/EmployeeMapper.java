package hu.cubix.hr.balage.mapper;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.model.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", source = "employee.id")
    @Mapping(target = "name", source = "employee.name")
    @Mapping(target = "positionName", source = "employee.position.name")
    @Mapping(target = "salary", source = "employee.salary")
    @Mapping(target = "companyName", source = "employee.company.name")
    EmployeeDto employeeToDto(Employee employee);

    @InheritInverseConfiguration
    Employee dtoToEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> employeesToDtos(List<Employee> employees);

    List<Employee> dtosToEmployees(List<EmployeeDto> employeeDtos);
}