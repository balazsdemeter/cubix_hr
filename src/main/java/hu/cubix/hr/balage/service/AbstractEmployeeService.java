package hu.cubix.hr.balage.service;

import hu.cubix.hr.balage.dto.EmployeeDto;
import hu.cubix.hr.balage.mapper.EmployeeMapper;
import hu.cubix.hr.balage.model.Employee;
import hu.cubix.hr.balage.model.Position;
import hu.cubix.hr.balage.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractEmployeeService implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final PositionService positionService;

    protected AbstractEmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.positionService = positionService;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeMapper.employeesToDtos(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto findById(Long id) {
        return employeeMapper.employeeToDto(employeeRepository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        if (findById(employeeDto.getId()) != null) {
            return null;
        }
        return save(employeeDto);
    }

    @Transactional
    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        if (findById(employeeDto.getId()) == null) {
            return null;
        }
        return save(employeeDto);
    }

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = createEmployeeEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.employeeToDto(savedEmployee);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<EmployeeDto> findByPositionName(String positionName, int pageNumber, int pageSize) {
        Page<Employee> employeePage = employeeRepository.findEmployeesByPositionName(positionName, PageRequest.of(pageNumber, pageSize));
        return new PageImpl<>(employeeMapper.employeesToDtos(employeePage.getContent()), employeePage.getPageable(), employeePage.getTotalElements());
    }

    @Override
    public List<EmployeeDto> findByWorkStartDateBetween(LocalDateTime from, LocalDateTime to) {
        List<Employee> employees = employeeRepository.findEmployeesByWorkStartBetween(from, to);
        return employeeMapper.employeesToDtos(employees);
    }

    @Override
    public List<EmployeeDto> findByNamePrefix(String namePrefix) {
        List<Employee> employees = employeeRepository.findByNameStartingWithIgnoreCase(namePrefix);
        return employeeMapper.employeesToDtos(employees);
    }

    @Override
    public List<EmployeeDto> findEmployeesByExample(EmployeeDto employeeDto) {

        Specification<Employee> specification = Specification.where(null);

        Long id = employeeDto.getId();
        if (id != null) {
            specification = specification.and(EmployeeSpecification.hasId(id));
        }

        String name = employeeDto.getName();
        if (StringUtils.hasLength(name)) {
            specification = specification.and(EmployeeSpecification.nameStartsWith(name));
        }

        String positionName = employeeDto.getPositionName();
        if (StringUtils.hasLength(positionName)) {
            specification = specification.and(EmployeeSpecification.hasPositionName(positionName));
        }

        Integer salary = employeeDto.getSalary();
        if (salary != null) {
            specification = specification.and(EmployeeSpecification.salaryBetween(salary));
        }

        LocalDateTime workStart = employeeDto.getWorkStart();
        if (workStart != null) {
            specification = specification.and(EmployeeSpecification.hasWorkStart(workStart));
        }

        String companyName = employeeDto.getCompanyName();
        if (StringUtils.hasLength(companyName)) {
            specification = specification.and(EmployeeSpecification.companyNameStartsWith(companyName));
        }

        List<Employee> employees = employeeRepository.findAll(specification);
        return employeeMapper.employeesToDtos(employees);
    }

    @Override
    public Employee createEmployeeEntity(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.dtoToEmployee(employeeDto);
        Position position = positionService.findByName(employeeDto.getPositionName());
        employee.setPosition(position);
        return employee;
    }
}