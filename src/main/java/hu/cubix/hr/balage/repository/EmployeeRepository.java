package hu.cubix.hr.balage.repository;

import hu.cubix.hr.balage.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Page<Employee> findEmployeesByPositionName(String positionName, Pageable pageable);

    List<Employee> findEmployeesByWorkStartBetween(LocalDateTime date1, LocalDateTime date2);

    List<Employee> findByNameStartingWithIgnoreCase(String prefix);

    List<Employee> findByName(String name);
}