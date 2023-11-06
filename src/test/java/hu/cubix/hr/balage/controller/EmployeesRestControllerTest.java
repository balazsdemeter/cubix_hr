package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.model.Position;
import hu.cubix.hr.balage.repository.CompanyRepository;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeesRestControllerTest {
    private static final String TEST_EMPLOYEE_NAME = UUID.randomUUID().toString();
    private static final String API_EMPLOYEES = "/api/employees";
    private final WebTestClient webTestClient;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final PositionRepository positionRepository;
    private final EmployeeMapper employeeMapper;
    private EmployeeDto employeeDto;
    private Long testId;

    @Autowired
    public EmployeesRestControllerTest(
            WebTestClient webTestClient,
            EmployeeRepository employeeRepository,
            CompanyRepository companyRepository,
            PositionRepository positionRepository,
            EmployeeMapper employeeMapper) {
        this.webTestClient = webTestClient;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.positionRepository = positionRepository;
        this.employeeMapper = employeeMapper;
    }

    @BeforeEach
    public void init() {
        employeeDto = new EmployeeDto(0L, TEST_EMPLOYEE_NAME, null, 150000, null, LocalDateTime.now().minusMonths(6));
    }

    @AfterEach
    public void delete() {
        List<Employee> employees = employeeRepository.findByName(TEST_EMPLOYEE_NAME);
        employees.forEach(employee -> employeeRepository.deleteById(employee.getId()));
    }

    @Test
    public void testPostOk() {
        employeeDto.setPositionName(positionRepository.findAll().get(0).getName());
        employeeDto.setCompanyName(companyRepository.findAll().get(0).getName());
        webTestClient
                .post()
                .uri(API_EMPLOYEES)
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPostNok() {
        webTestClient
                .post()
                .uri(API_EMPLOYEES)
                .bodyValue(new EmployeeDto())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testPutOk() {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Company company = companyRepository.findAll().get(0);
        employee.setCompany(company);
        Position position = positionRepository.findAll().get(0);
        employee.setPosition(position);
        Employee savedEmployee = employeeRepository.save(employee);
        testId = savedEmployee.getId();

        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + testId)
                .bodyValue(new EmployeeDto(0L, TEST_EMPLOYEE_NAME, position.getName(), 1, company.getName(), LocalDateTime.now().minusMonths(12)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPutBadRequest() {
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + testId)
                .bodyValue(new EmployeeDto())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testPutNotFound() {
        employeeDto.setPositionName(positionRepository.findAll().get(0).getName());
        employeeDto.setCompanyName(companyRepository.findAll().get(0).getName());
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/98798")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}