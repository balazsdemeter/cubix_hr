package hu.cubix.hr.balage.model;

import hu.cubix.hr.balage.model.enums.CompanyForm;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private Integer registrationNumber;
    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private CompanyForm companyForm;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Company() {
    }

    public Company(Long id, Integer registrationNumber, String name, String address, CompanyForm companyForm) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.companyForm = companyForm;
    }

    public Company(Long id, Integer registrationNumber, String name, String address, CompanyForm companyForm, List<Employee> employees) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.companyForm = companyForm;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CompanyForm getCompanyForm() {
        return companyForm;
    }

    public void setCompanyForm(CompanyForm companyForm) {
        this.companyForm = companyForm;
    }

    public List<Employee> getEmployees() {
        if (this.employees == null)
            this.employees = new ArrayList<>();
        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", registrationNumber=" + registrationNumber +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}