package br.com.api.flow.supplier.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.SupplierMapper;
import br.com.api.entity.Supplier;
import br.com.api.entity.repository.SupplierFilter;
import br.com.api.entity.repository.SupplierRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindSupplierByFilterFlowItem {

	@Autowired
	private SupplierRepositoryImpl supplierRepositoryImpl;

	@Autowired
	private SupplierMapper supplierMapper;

	public SupplierFilter findByFilter(SupplierFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Supplier> entities = supplierRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> supplierMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Supplier> entities = supplierRepositoryImpl.findByFilter(filter);
			filter.setResult(
					entities.stream().map(entity -> supplierMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
