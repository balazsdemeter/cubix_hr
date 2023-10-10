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
        test(121, 192500);
        test(61, 183750);
        test(59, 176750);
        test(29, 175000);
    }

    private void test(long months, Integer expectedSalary) {
        var employee = createEmployee(months);
        salaryService.setSalary(employee);
        System.out.println("Expected salary: " + expectedSalary + ", calculated salary: " + employee.getSalary());
    }

    private Employee createEmployee(long months) {
        return new Employee(1L, "Test Elek", "munka", 175000, LocalDateTime.now().minusMonths(months));
    }
}