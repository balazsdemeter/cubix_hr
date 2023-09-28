package hu.cubix.hr.balage;

import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.service.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    private SalaryServiceImpl salaryService;

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var employee = createEmployee();
        salaryService.setSalary(employee);
        System.out.println(employee.getSalary());
    }

    private Employee createEmployee() {
        return new Employee(1L, "munka", 175000, LocalDateTime.now().minusYears(5).minusMonths(7));
    }
}