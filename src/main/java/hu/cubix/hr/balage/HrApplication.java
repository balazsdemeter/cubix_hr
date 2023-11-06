package hu.cubix.hr.balage;

import hu.cubix.hr.balage.service.InitDbService;
import hu.cubix.hr.balage.service.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    private final InitDbService initDbService;

    private final SalaryServiceImpl salaryService;

    @Autowired
    public HrApplication(InitDbService initDbService, SalaryServiceImpl salaryService) {
        this.initDbService = initDbService;
        this.salaryService = salaryService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HrApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        test(121, 192500);
//        test(61, 183750);
//        test(59, 176750);
//        test(29, 175000);

        initDbService.clearDB();
        initDbService.insertTestData();
    }

//    private void test(long months, Integer expectedSalary) {
//        var employee = createEmployee(months);
//        salaryService.setSalary(employee);
//        System.out.println("Expected salary: " + expectedSalary + ", calculated salary: " + employee.getSalary());
//    }

//    private Employee createEmployee(long months) {
//        return new Employee("Test Elek", "munka", 175000, LocalDateTime.now().minusMonths(months));
//    }
}