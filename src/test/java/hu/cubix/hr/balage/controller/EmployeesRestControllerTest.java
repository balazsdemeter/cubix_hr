package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeesRestControllerTest {

    private static final String API_EMPLOYEES = "/api/employees";

    private final WebTestClient webTestClient;

    private static EmployeeDto employeeDto;

    @BeforeAll
    public static void init() {
        employeeDto = new EmployeeDto(2L, "Teszt Ernő", "Tesztelő", 150000, LocalDateTime.now().minusMonths(6));
    }

    @Autowired
    public EmployeesRestControllerTest(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
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
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/1")
                .bodyValue(new EmployeeDto(1L, "Teszt Elek", "Tesztelő", 200000, LocalDateTime.now().minusMonths(6)))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void testPutBadRequest() {
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/1")
                .bodyValue(new EmployeeDto())
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testPutNotFound() {
        webTestClient
                .put()
                .uri(API_EMPLOYEES + "/2")
                .bodyValue(employeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }
}