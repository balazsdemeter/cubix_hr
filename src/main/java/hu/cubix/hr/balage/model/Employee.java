package hu.cubix.hr.balage.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private Position position;
    private Integer salary;
    private LocalDateTime workStart;
    @ManyToOne
    private Company company;

    public Employee() {
    }

    public Employee(String name, Position position, Integer salary, LocalDateTime workStart, Company company) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.workStart = workStart;
        this.company = company;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}