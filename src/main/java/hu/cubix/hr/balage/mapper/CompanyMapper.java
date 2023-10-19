package hu.cubix.hr.balage.mapper;

import hu.cubix.hr.balage.dto.CompanyDto;
import hu.cubix.hr.balage.model.Company;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto companyToDto(Company company);

    Company dtoToCompany(CompanyDto companyDto);

    List<CompanyDto> companiesToDtos(List<Company> companies);

    List<Company> dtosToCompanies(List<CompanyDto> companyDtos);
}
