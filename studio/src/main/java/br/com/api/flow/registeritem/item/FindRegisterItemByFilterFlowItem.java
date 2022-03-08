package br.com.api.flow.registeritem.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterItemMapper;
import br.com.api.entity.RegisterItem;
import br.com.api.entity.repository.RegisterItemFilter;
import br.com.api.entity.repository.RegisterItemRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindRegisterItemByFilterFlowItem {

	@Autowired
	private RegisterItemRepositoryImpl registerItemRepositoryImpl;

	@Autowired
	private RegisterItemMapper registerItemMapper;

	public RegisterItemFilter findByFilter(RegisterItemFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<RegisterItem> entities = registerItemRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> registerItemMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<RegisterItem> entities = registerItemRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> registerItemMapper.toDTO(entity)).toList());
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
