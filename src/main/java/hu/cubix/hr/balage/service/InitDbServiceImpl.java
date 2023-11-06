package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.enums.CompanyForm;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.model.Position;
import hu.cubix.hr.balage.model.enums.Qualification;
import hu.cubix.hr.balage.repository.CompanyRepository;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InitDbServiceImpl implements InitDbService {
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public InitDbServiceImpl(
            EmployeeRepository employeeRepository,
            CompanyRepository companyRepository,
            PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.positionRepository = positionRepository;
    }

    @Override
    public void clearDB() {
        employeeRepository.deleteAll();
        companyRepository.deleteAll();
        positionRepository.deleteAll();
    }

    @Override
    public void insertTestData() {
        for (int i = 1; i < 6; i++) {
            RandomEnumGenerator randomCompanyForm = new RandomEnumGenerator(CompanyForm.class);
            CompanyForm companyForm = (CompanyForm) randomCompanyForm.randomEnum();
            Company company = new Company(0L, 1234, "CÉG " + i, "BUDAPEST 1234", companyForm);

            RandomEnumGenerator randomQualification = new RandomEnumGenerator(Qualification.class);
            Qualification qualification = (Qualification) randomQualification.randomEnum();
            Position position = new Position("TESZT MUNKA " + i, qualification, 25000 * i);
            positionRepository.save(position);

            List<Employee> employees = new ArrayList<>();
            for (int j = 1; j < 6; j++) {
                Employee employee = new Employee("TESZT ELEK " + i + j, position, 15000 * i * j, LocalDateTime.now().minusMonths(j), company);
                employee.setPosition(position);
                employees.add(employee);
            }

            employees.forEach(employee -> employee.setCompany(company));
            companyRepository.save(company);
            employeeRepository.saveAll(employees);
        }
    }

    public static class RandomEnumGenerator<T extends Enum<T>> {
        private static final Random PRNG = new Random();
        private final T[] values;

        public RandomEnumGenerator(Class<T> e) {
            values = e.getEnumConstants();
        }

        public T randomEnum() {
            return values[PRNG.nextInt(values.length)];
        }
    }
}