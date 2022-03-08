package br.com.api.flow.student.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.StudentMapper;
import br.com.api.entity.Student;
import br.com.api.entity.repository.StudentFilter;
import br.com.api.entity.repository.StudentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindStudentByFilterFlowItem {

	@Autowired
	private StudentRepositoryImpl studentRepositoryImpl;

	@Autowired
	private StudentMapper studentMapper;

	public StudentFilter findByFilter(StudentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Student> entities = studentRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> studentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Student> entities = studentRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> studentMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
