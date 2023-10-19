package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractEmployee implements EmployeeService {

    protected Map<Long, Employee> employees = new HashMap<>();

    {
        employees.put(1L, new Employee(1L, "Teszt Elek", "Tesztelő", 100000, LocalDateTime.now().minusMonths(6)));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Employee findById(Long id) {
        return employees.get(id);
    }

    @Override
    public Employee create(Employee employee) {
        if (findById(employee.getId()) != null) {
            return null;
        }
        return save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        if (findById(employee.getId()) == null) {
            return null;
        }
        return save(employee);
    }

    @Override
    public Employee save(Employee employee) {
        employees.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public void delete(Long id) {
        employees.remove(id);
    }
}