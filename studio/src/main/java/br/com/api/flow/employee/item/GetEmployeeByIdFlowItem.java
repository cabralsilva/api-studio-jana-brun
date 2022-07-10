package br.com.api.flow.employee.item;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.Employee;
import br.com.api.entity.repository.EmployeeRepository;

@Component
public class GetEmployeeByIdFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	public Optional<EmployeeDTO> get(Integer identifier) {

		final Optional<Employee> employee = employeeRepository.findById(identifier);
		
		if (employee.isPresent()) {
			return Optional.ofNullable(employeeMapper.toDTO(employee.get()));
		}
		
		return Optional.empty();
	}
}
