package hu.cubix.hr.balage.model;

import hu.cubix.hr.balage.model.enums.Qualification;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.Objects;

@Entity
public class Position {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Qualification qualification;
    private Integer minimalSalary;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id) && Objects.equals(name, position.name) && qualification == position.qualification && Objects.equals(minimalSalary, position.minimalSalary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, qualification, minimalSalary);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", qualification=" + qualification +
                ", minimalSalary=" + minimalSalary +
                '}';
    }
}