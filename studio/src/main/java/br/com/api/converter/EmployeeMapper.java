package br.com.api.converter;

import org.mapstruct.Mapper;

import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	public Employee toEntity(EmployeeDTO dto);

	public EmployeeDTO toDTO(Employee entity);
}
