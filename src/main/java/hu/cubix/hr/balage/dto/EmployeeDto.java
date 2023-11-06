package hu.cubix.hr.balage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class EmployeeDto {
    private Long id;
    @NotEmpty
    String name;
    @NotEmpty
    String positionName;
    @Positive
    Integer salary;
    @NotEmpty
    String companyName;
    @Past
    LocalDateTime workStart;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String name, String positionName, Integer salary, String companyName, LocalDateTime workStart) {
        this.id = id;
        this.name = name;
        this.positionName = positionName;
        this.salary = salary;
        this.companyName = companyName;
        this.workStart = workStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
    }
}