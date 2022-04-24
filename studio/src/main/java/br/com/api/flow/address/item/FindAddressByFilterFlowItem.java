package br.com.api.flow.address.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.AddressMapper;
import br.com.api.entity.Address;
import br.com.api.entity.repository.AddressFilter;
import br.com.api.entity.repository.AddressRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindAddressByFilterFlowItem {

	@Autowired
	private AddressRepositoryImpl addressRepositoryImpl;

	@Autowired
	private AddressMapper addressMapper;

	public AddressFilter findByFilter(AddressFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Address> entities = addressRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> addressMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Address> entities = addressRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> addressMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
