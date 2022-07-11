package br.com.api.flow.matriculationitem.item;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.MatriculationItemMapper;
import br.com.api.entity.MatriculationItem;
import br.com.api.entity.repository.MatriculationItemFilter;
import br.com.api.entity.repository.MatriculationItemRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.exceptions.FindByFilterExceptionEnum;

@Component
public class FindMatriculationItemByFilterFlowItem {

	@Autowired
	private MatriculationItemRepositoryImpl MatriculationItemRepositoryImpl;

	@Autowired
	private MatriculationItemMapper MatriculationItemMapper;

	public MatriculationItemFilter findByFilter(MatriculationItemFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<MatriculationItem> entities = MatriculationItemRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> MatriculationItemMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<MatriculationItem> entities = MatriculationItemRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> MatriculationItemMapper.toDTO(entity))
					.collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(FindByFilterExceptionEnum.MORE_THAN_ONE_MATRICULATION_FOUND);
		}

		return filter;
	}
}
