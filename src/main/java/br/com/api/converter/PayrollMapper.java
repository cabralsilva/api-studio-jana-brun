package br.com.api.converter;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.dto.PersonDTO;
import br.com.api.entity.Employee;
import br.com.api.entity.Payroll;
import br.com.api.entity.PayrollDetail;
import br.com.api.entity.Person;

@Mapper(componentModel = "spring")
public interface PayrollMapper {

	public Payroll toEntity(PayrollDTO source);

	@Mapping(source = "payrollDetailList", target = "payrollDetailList", qualifiedByName = "toPayrollDetailListDTO")
	public PayrollDTO toDTO(Payroll source);

	@Named("toPayrollDetailListDTO")
	@IterableMapping(qualifiedByName = "toPayrollDetailDTO")
	public List<PayrollDetailDTO> toPayrollDetailListDTO(List<PayrollDetail> source);

	@Named("toPayrollDetailDTO")
	public default PayrollDetailDTO toPayrollDetailDTO(PayrollDetail source) {
		if (source == null) {
			return null;
		}

		PayrollDetailDTO target = PayrollDetailDTO.builder().build();

		target.setIdentifier(source.getIdentifier());
		target.setValue(source.getValue());
		target.setDescription(source.getDescription());
		target.setEmployee(toEmployeeDTO(source.getEmployee()));
		;

		return target;
	}

	@Named("toEmployeeDTO")
	@Mapping(source = "person", target = "person", qualifiedByName = "toPersonDTO")
	public default EmployeeDTO toEmployeeDTO(Employee source) {
		if (source == null) {
			return null;
		}

		EmployeeDTO target = EmployeeDTO.builder().build();

		target.setIdentifier(source.getIdentifier());
		target.setEmail(source.getEmail());
		target.setPerson(toPersonDTO(source.getPerson()));

		return target;
	}

	@Named("toPersonDTO")
	public default PersonDTO toPersonDTO(Person source) {
		if (source == null) {
			return null;
		}

		PersonDTO target = PersonDTO.builder().build();

		target.setIdentifier(source.getIdentifier());
		target.setName(source.getName());
		target.setNickName(source.getNickName());

		return target;
	}
}
