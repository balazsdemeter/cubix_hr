package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.mapper.CompanyMapper;
import hu.cubix.hr.balage.model.Company;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    private final CompanyMapper companyMapper;

    private final EmployeeService employeeService;

    @Autowired
    public CompanyService(CompanyRepository repository,
                          CompanyMapper companyMapper,
                          EmployeeService employeeService) {
        this.repository = repository;
        this.companyMapper = companyMapper;
        this.employeeService = employeeService;
    }

    @Transactional
    public List<CompanyDto> findAll() {
        return companyMapper.companiesToDtos(repository.findAll());
    }

    @Transactional
    public CompanyDto findById(Long id) {
        return companyMapper.companyToDto(findCompanyById(id));
    }

    private Company findCompanyById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public CompanyDto create(CompanyDto companyDto) {
        if (findCompanyById(companyDto.id()) != null) {
            return null;
        }

        Company company = save(companyMapper.dtoToCompany(companyDto));
        return companyMapper.companyToDto(company);
    }

    @Transactional
    public CompanyDto update(CompanyDto companyDto) {
        if (findById(companyDto.id()) == null) {
            return null;
        }

        Company company = save(companyMapper.dtoToCompany(companyDto));
        return companyMapper.companyToDto(company);
    }

    @Transactional
    public Company save(Company company) {
        return repository.save(company);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void delete(Long companyId, Long employeeId) {
        Company company = findCompanyById(companyId);
        if (company != null) {
            company.getEmployees().removeIf(employee -> employee.getId().equals(employeeId));
        }
    }

    @Transactional
    public CompanyDto addEmployee(Long id, EmployeeDto employeeDto) {
        Company company = findCompanyById(id);
        if (company == null) {
            return null;
        }

        Employee employee = employeeService.createEmployeeEntity(employeeDto);
        employee.setCompany(company);
        company.getEmployees().add(employee);

        Company savedCompany = save(company);
        return companyMapper.companyToDto(savedCompany);
    }

    @Transactional
    public CompanyDto refreshEmployees(Long id, List<EmployeeDto> employeeDtos) {
        Company company = findCompanyById(id);
        if (company == null) {
            return null;
        }
        List<Employee> oldEmployees = company.getEmployees();
        oldEmployees.forEach(employee -> employee.setCompany(null));
        oldEmployees.clear();

        employeeDtos.forEach(employeeDto -> oldEmployees.add(employeeService.createEmployeeEntity(employeeDto)));
        Company savedCompany = save(company);
        return companyMapper.companyToDto(savedCompany);
    }

    @Transactional
    public List<CompanyDto> findByEmployeeNumber(Integer employeeNumber) {
        return companyMapper.companiesToDtos(repository.findCompaniesByEmployeeNumber(employeeNumber));
    }

    @Transactional
    public List<CompanyDto> findByEmployeeSalaryGreaterThan(Integer salary) {
        return companyMapper.companiesToDtos(repository.findCompaniesByEmployeeSalaryGreaterThan(salary));
    }

    @Transactional
    public CompanyDto findByName(String name) {
        return companyMapper.companyToDto(repository.findCompanyByName(name));
    }

    @Transactional
    public List<Double> averageSalaryByCompanyId(Long id) {
        return repository.averageSalaryByCompanyId(id);
    }
}