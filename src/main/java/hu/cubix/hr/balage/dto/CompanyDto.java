package hu.cubix.hr.balage.dto;

import java.util.ArrayList;
import java.util.List;

public record CompanyDto(Long id, Integer registrationNumber, String name, String address,
                         List<EmployeeDto> employees) {
    public CompanyDto() {
        this(0L, null, null, null, new ArrayList<>());
    }

    public CompanyDto(Long id, Integer registrationNumber, String name, String address, List<EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }
}