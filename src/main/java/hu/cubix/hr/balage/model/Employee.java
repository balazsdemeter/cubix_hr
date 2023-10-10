package hu.cubix.hr.balage.model;

import java.time.LocalDateTime;

public class Employee {

    private Long id;
    private String name;
    private String job;
    private Integer salary;
    private LocalDateTime workStart;

    public Employee() {
    }

    public Employee(Long id, String name, String job, Integer salary, LocalDateTime workStart) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
