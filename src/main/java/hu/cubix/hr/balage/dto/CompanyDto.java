package hu.cubix.hr.balage.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public record CompanyDto(Long id, @NotNull Integer registrationNumber, @NotNull String name, @NotNull String address, @NotNull String companyForm,
                         List<EmployeeDto> employees) {
    public CompanyDto() {
        this(0L, null, null, null, null, new ArrayList<>());
    }

    public CompanyDto(Long id, Integer registrationNumber, String name, String address, String companyForm, List<EmployeeDto> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.companyForm = companyForm;
        this.employees = employees;
    }
}