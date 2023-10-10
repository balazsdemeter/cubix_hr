package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HrController {

    private List<Employee> employees = new ArrayList<>();

    {
        employees.add(new Employee(1L, "Teszt Elek", "munka", 175000, LocalDateTime.now().minusMonths(5)));
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employees);
        model.put("newEmployee", new Employee());

        return "employees";
    }

    @PostMapping("/updateEmployee/{id}")
    public String updateEmployeeAction(Employee updatedEmployee) {
        Employee employee = getEmployee(updatedEmployee.getId());
        employee.setName(updatedEmployee.getName());
        employee.setJob(updatedEmployee.getJob());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setWorkStart(updatedEmployee.getWorkStart());

        return "redirect:/";
    }

    @GetMapping("/updateEmployee/{id}")
    public String updateEmployeeView(Map<String, Object> model, @PathVariable Long id) {
        Employee employee = getEmployee(id);
        model.put("employee", employee);

        return "employee";
    }

    @PostMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        Employee employee = getEmployee(id);
        employees.remove(employee);

        return "redirect:/";
    }

    @PostMapping("/createEmployee")
    public String createEmployee(Employee employee) {
        employee.setWorkStart(LocalDateTime.now());
        employees.add(employee);
        return "redirect:/";
    }

    private Employee getEmployee(Long id) {
        return employees.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(new Employee());
    }
}