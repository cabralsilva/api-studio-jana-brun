package br.com.api.flow.product.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_REGISTER_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.ProductMapper;
import br.com.api.entity.Product;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.entity.repository.ProductRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindProductByFilterFlowItem {

	@Autowired
	private ProductRepositoryImpl productRepositoryImpl;

	@Autowired
	private ProductMapper productMapper;

	public ProductFilter findByFilter(ProductFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<Product> entities = productRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> productMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<Product> entities = productRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> productMapper.toDTO(entity)).toList());			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_REGISTER_FOUND);
		}

		return filter;
	}
}
