package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(source = "dto.person", target = "person")
	public Employee toEntity(EmployeeDTO dto);

	public EmployeeDTO toDTO(Employee entity);
}
