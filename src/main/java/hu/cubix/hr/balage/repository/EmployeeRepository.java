package hu.cubix.hr.balage.repository;

import hu.cubix.hr.balage.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findEmployeesByJob(String job);

    List<Employee> findEmployeesByWorkStartBetween(LocalDateTime date1, LocalDateTime date2);

    @Query(value = "select e from Employee e where UPPER(e.name) like :prefix%")
    List<Employee> findEmployeesByNameStartsWith(String prefix);
}