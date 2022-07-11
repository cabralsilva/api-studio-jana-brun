package br.com.api.flow.employee.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.repository.EmployeeRepository;

@Component
public class UpdateEmployeeFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	public EmployeeDTO update(EmployeeDTO employee) {

		return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employee)));
	}
}
