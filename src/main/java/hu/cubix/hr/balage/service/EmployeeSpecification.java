package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Company_;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.model.Employee_;
import hu.cubix.hr.balage.model.Position_;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeSpecification {

    public static Specification<Employee> hasId(long id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Employee_.id), id);
    }

    public static Specification<Employee> nameStartsWith(String prefix) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Employee_.name)), prefix.toLowerCase() + "%");
    }

    public static Specification<Employee> hasPositionName(String positionName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Employee_.position).get(Position_.name), positionName);
    }

    public static Specification<Employee> salaryBetween(Integer salary) {
        double amount = salary * 0.05;
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(criteriaBuilder.toDouble(root.get(Employee_.salary)), salary - amount, salary + amount);
    }

    public static Specification<Employee> companyNameStartsWith(String prefix) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(Employee_.company).get(Company_.name)), prefix.toLowerCase() + "%");
    }

    public static Specification<Employee> hasWorkStart(LocalDateTime workStart) {
        LocalDateTime startOfDay = workStart.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = workStart.plusDays(1).toLocalDate().atStartOfDay();
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Employee_.workStart), startOfDay, endOfDay);
    }
}