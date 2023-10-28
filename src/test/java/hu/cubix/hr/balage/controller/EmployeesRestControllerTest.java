package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeesRestControllerTest {

    private static final String JOB_NAME = "Teszt munka";
    private static final String API_EMPLOYEES = "/api/employees";
    private final WebTestClient webTestClient;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private EmployeeDto employeeDto;
    private Long testId;

    @Autowired
    public EmployeesRestControllerTest(WebTestClient webTestClient, EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.webTestClient = webTestClient;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @BeforeEach
    public void init() {
        employeeDto = new EmployeeDto("Teszt Ernő", JOB_NAME, 150000, LocalDateTime.now().minusMonths(6));
    }

    @AfterEach
    public void delete() {
        List<Employee> employees = employeeRepository.findEmployeesByJob(JOB_NAME);
        employees.forEach(employee -> employeeRepository.deleteById(employee.getId()));
    }

    @Test
    public void testPostOk() {
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
        Employee save = employeeRepository.save(employeeMapper.dtoToEmployee(employeeDto));
        testId = save.getId();

        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/" + testId)
                .bodyValue(new EmployeeDto("Teszt Elek Update", JOB_NAME, 200000, LocalDateTime.now().minusMonths(6)))
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
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/98798")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}