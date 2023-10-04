package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HrController {

    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1L, "munka", 175000, LocalDateTime.now().minusMonths(5)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());

        return "index";
    }

    @PostMapping("/employee")
    public String addEmployee(Employee employee) {
        employee.setWorkStart(LocalDateTime.now());
        employees.add(employee);
        return "redirect:/";
    }
}