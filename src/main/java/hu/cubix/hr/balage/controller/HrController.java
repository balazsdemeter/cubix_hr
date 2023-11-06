package hu.cubix.hr.balage.controller;

import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class HrController {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public HrController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("employees", employeeRepository.findAll());
        model.put("newEmployee", new Employee());

        return "employees";
    }

    @PostMapping("/updateEmployee/{id}")
    public String updateEmployeeAction(Employee updatedEmployee) {
        Employee employee = getEmployee(updatedEmployee.getId());
        employee.setName(updatedEmployee.getName());
        employee.setCompany(updatedEmployee.getCompany());
        employee.setSalary(updatedEmployee.getSalary());
        employee.setCompany(updatedEmployee.getCompany());
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
        employeeRepository.delete(employee);

        return "redirect:/";
    }

    @PostMapping("/createEmployee")
    public String createEmployee(Employee employee) {
        employee.setWorkStart(LocalDateTime.now());
        employeeRepository.save(employee);
        return "redirect:/";
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}