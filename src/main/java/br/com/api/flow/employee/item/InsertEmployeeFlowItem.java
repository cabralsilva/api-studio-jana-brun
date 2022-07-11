package br.com.api.flow.employee.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.repository.EmployeeRepository;

@Component
public class InsertEmployeeFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UpdateEmployeeFlowItem updateEmployeeFlowItem;

	@Autowired
	private EmployeeMapper employeeMapper;

	public EmployeeDTO insert(@NonNull EmployeeDTO employee) {

		if (Objects.nonNull(employee.getIdentifier())) {
			return updateEmployeeFlowItem.update(employee);
		}

		return employeeMapper.toDTO(employeeRepository.save(employeeMapper.toEntity(employee)));
	}
}
