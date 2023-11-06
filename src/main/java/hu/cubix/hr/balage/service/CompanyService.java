package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.CompanyRepository;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import hu.cubix.hr.balage.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository repository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;

    @Autowired
    public CompanyService(
            CompanyRepository repository,
            EmployeeRepository employeeRepository,
            PositionRepository positionRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
    }

    public List<Company> findAll() {
        return repository.findAll();
    }

    public Company findById(Long id) {
        return repository.findById(id).orElse(null);
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
        return repository.save(company);
    }

    public void delete(Long id) {
        repository.deleteById(id);
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

        manageEmployee(employee, company);

        company.getEmployees().add(employee);
        return company;
    }

    private void manageEmployee(Employee employee, Company company) {
        employee.setCompany(company);
        employee.setPosition(positionRepository.findByName(employee.getPosition().getName()));
        employeeRepository.save(employee);
    }

    @Transactional
    public Company refreshEmployees(Long id, List<Employee> newEmployees) {
        Company company = findById(id);
        if (company == null) {
            return null;
        }
        List<Employee> oldEmployees = company.getEmployees();
        oldEmployees.forEach(employee -> employee.setCompany(null));
        oldEmployees.clear();

        newEmployees.forEach(employee -> manageEmployee(employee, company));
        oldEmployees.addAll(newEmployees);
        return company;
    }

    public List<Company> findByEmployeeNumber(Integer employeeNumber) {
        return repository.findCompaniesByEmployeeNumber(employeeNumber);
    }

    public List<Company> findByEmployeeSalaryGreaterThan(Integer salary) {
        return repository.findCompaniesByEmployeeSalaryGreaterThan(salary);
    }

    public Company findByName(String name) {
        return repository.findCompanyByName(name);
    }

    public List<Double> averageSalaryByCompanyId(Long id) {
        return repository.averageSalaryByCompanyId(id);
    }
}