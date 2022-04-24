package br.com.api.flow.employee.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.EmployeeMapper;
import br.com.api.entity.Employee;
import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.entity.repository.EmployeeRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindEmployeeByFilterFlowItem {

	@Autowired
	private EmployeeRepositoryImpl employeeRepositoryImpl;

	@Autowired
	private EmployeeMapper employeeMapper;

	public EmployeeFilter findByFilter(EmployeeFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Employee> entities = employeeRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> employeeMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Employee> entities = employeeRepositoryImpl.findByFilter(filter);
			filter.setResult(
					entities.stream().map(entity -> employeeMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
