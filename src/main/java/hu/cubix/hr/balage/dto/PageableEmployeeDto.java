package hu.cubix.hr.balage.dto;

import java.util.ArrayList;
import java.util.List;

public class PageableEmployeeDto {
    private List<EmployeeDto> employeeDtos;
    private int totalPages;
    private long totalElements;

    public PageableEmployeeDto() {
    }

    public PageableEmployeeDto(List<EmployeeDto> employeeDtos, int totalPages, long totalElements) {
        this.employeeDtos = employeeDtos;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<EmployeeDto> getEmployeeDtos() {
        if (employeeDtos == null) {
            return new ArrayList<>();
        }
        return employeeDtos;
    }

    public void setEmployeeDtos(List<EmployeeDto> employeeDtos) {
        this.employeeDtos = employeeDtos;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}