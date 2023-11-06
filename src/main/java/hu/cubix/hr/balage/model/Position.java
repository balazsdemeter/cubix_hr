package hu.cubix.hr.balage.model;

import hu.cubix.hr.balage.model.enums.Qualification;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Qualification qualification;
    private Integer minimalSalary;
    @OneToMany(mappedBy = "position")
    private List<Employee>  employees;

    public Position() {
    }

    public Position(String name, Qualification qualification, Integer minimalSalary) {
        this.name = name;
        this.qualification = qualification;
        this.minimalSalary = minimalSalary;
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

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }

    public Integer getMinimalSalary() {
        return minimalSalary;
    }

    public void setMinimalSalary(Integer minimalSalary) {
        this.minimalSalary = minimalSalary;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}