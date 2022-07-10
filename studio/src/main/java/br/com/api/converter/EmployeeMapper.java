package br.com.api.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.security.Credential;
import br.com.api.entity.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(source = "dto.person", target = "person")
	public Employee toEntity(EmployeeDTO dto);

	@Mapping(source = "username", target = "email")
	public Employee toEntity(Credential source);

	public EmployeeDTO toDTO(Employee entity);

	@Mapping(source = "email", target = "username")
	public Credential toCredential(EmployeeDTO source);
}
