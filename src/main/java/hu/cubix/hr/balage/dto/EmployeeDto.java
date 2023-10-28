package hu.cubix.hr.balage.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EmployeeDto(@NotNull Long id, @NotEmpty String name, @NotEmpty String job, @Positive Integer salary,
                          @Past LocalDateTime workStart) {
    public EmployeeDto() {
        this(0L, null, null, null, null);
    }

    public EmployeeDto(String name, String job, Integer salary, LocalDateTime workStart) {
        this(0L, name, job, salary, workStart);
    }

    public EmployeeDto(Long id, String name, String job, Integer salary, LocalDateTime workStart) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.salary = salary;
        this.workStart = workStart;
    }
}