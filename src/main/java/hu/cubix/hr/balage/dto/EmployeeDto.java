package hu.cubix.hr.balage.dto;

import java.time.LocalDateTime;

public class EmployeeDto {

    private Long id;
    private String job;
    private Integer salary;
    private LocalDateTime workStart;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, String job, Integer salary, LocalDateTime workStart) {
        this.id = id;
        this.job = job;
        this.salary = salary;
        this.workStart = workStart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public LocalDateTime getWorkStart() {
        return workStart;
    }

    public void setWorkStart(LocalDateTime workStart) {
        this.workStart = workStart;
    }
}
