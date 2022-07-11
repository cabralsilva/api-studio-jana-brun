package br.com.api.flow.employee.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.entity.Employee;
import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.entity.repository.EmployeeRepository;
import lombok.var;

@Component
public class SearchEmployeeByFilterFlowItem {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	public EmployeeFilter search(EmployeeFilter filter) {

		Example<Employee> example = Example.of(employeeMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = employeeRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> employeeMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = employeeRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> employeeMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
