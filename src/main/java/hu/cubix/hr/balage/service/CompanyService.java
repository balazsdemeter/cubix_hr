package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CompanyService {

    private Map<Long, Company> companyDtos = new TreeMap<>();

    public List<Company> findAll() {
        return new ArrayList<>(companyDtos.values());
    }

    public Company findById(Long id) {
        return companyDtos.get(id);
    }

    public Company create(Company company) {
        if (findById(company.getId()) != null) {
            return null;
        }

        return save(company);
    }

    public Company update(Company company) {
        if (findById(company.getId()) == null) {
            return null;
        }

        return save(company);
    }

    public Company save(Company company) {
        companyDtos.put(company.getId(), company);
        return company;
    }

    public void delete(Long id) {
        companyDtos.remove(id);
    }

    public void delete(Long companyId, Long employeeId) {
        Company company = findById(companyId);
        if (company != null) {
            company.getEmployees().removeIf(employee -> employee.getId().equals(employeeId));
        }
    }

    public Company addEmployee(Long id, Employee employee) {
        Company company = findById(id);
        if (company == null) {
            return null;
        }

        company.getEmployees().add(employee);
        return company;
    }

    public Company refreshEmployees(Long id, List<Employee> newEmployees) {
        Company company = findById(id);
        if (company == null) {
            return null;
        }

        List<Employee> oldEmployees = company.getEmployees();
        oldEmployees.clear();
        oldEmployees.addAll(newEmployees);
        return company;
    }
}