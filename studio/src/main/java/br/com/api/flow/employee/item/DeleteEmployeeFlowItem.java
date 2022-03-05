package br.com.api.flow.employee.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.EmployeeRepository;

@Component
public class DeleteEmployeeFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void delete(Integer identifier) {

		employeeRepository.deleteById(identifier);
	}
}
