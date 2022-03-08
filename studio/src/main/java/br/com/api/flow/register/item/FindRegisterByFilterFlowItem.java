package br.com.api.flow.register.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.RegisterMapper;
import br.com.api.entity.Register;
import br.com.api.entity.repository.RegisterFilter;
import br.com.api.entity.repository.RegisterRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindRegisterByFilterFlowItem {

	@Autowired
	private RegisterRepositoryImpl registerRepositoryImpl;

	@Autowired
	private RegisterMapper registerMapper;

	public RegisterFilter findByFilter(RegisterFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Register> entities = registerRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> registerMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Register> entities = registerRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> registerMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
