package hu.cubix.hr.balage.repository;

import hu.cubix.hr.balage.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByName(String name);

    @Query("select c from Employee e join e.company c where :salary < e.salary")
    List<Company> findCompaniesByEmployeeSalaryGreaterThan(@Param("salary") Integer salary);

    @Query("select c from Company c where size(c.employees) >= :size")
    List<Company> findCompaniesByEmployeeNumber(@Param("size") Integer size);

    @Query(value = "select avg(e.salary) as averageSalary from company c " +
            "join employee e ON e.company_id = c.id " +
            "join position p on p.id=e.position_id " +
            "where c.id=:id " +
            "group by p.id " +
            "order by averageSalary desc",
            nativeQuery = true)
    List<Double> averageSalaryByCompanyId(@Param("id") Long id);
}