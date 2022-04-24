package br.com.api.flow.classroom.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.ClassroomMapper;
import br.com.api.entity.Classroom;
import br.com.api.entity.repository.ClassroomFilter;
import br.com.api.entity.repository.ClassroomRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindClassroomByFilterFlowItem {

	@Autowired
	private ClassroomRepositoryImpl classroomRepositoryImpl;

	@Autowired
	private ClassroomMapper classroomMapper;

	public ClassroomFilter findByFilter(ClassroomFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Classroom> entities = classroomRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> classroomMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Classroom> entities = classroomRepositoryImpl.findByFilter(filter);
			filter.setResult(
					entities.stream().map(entity -> classroomMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
