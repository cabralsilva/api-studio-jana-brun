package br.com.api.flow.product.item;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.ProductMapper;
import br.com.api.entity.Product;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.entity.repository.ProductRepository;
import br.com.api.entity.repository.ProductRepositoryImpl;
import lombok.var;

@Component
public class FindProductByFilterFlowItem {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductRepositoryImpl productRepositoryImpl;

	@Autowired
	private ProductMapper productMapper;

	public ProductFilter findByFilter(ProductFilter filter) {

		Example<Product> example = Example.of(productMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = productRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> productMapper.toDTO(p)).collect(Collectors.toList()));
			return filter;
		}
		final var ret = productRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> productMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	public ProductFilter findByFilterImpl(ProductFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = productRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> productMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = productRepositoryImpl.findByFilter(filter);
		filter.setResult(ret.stream().map(p -> productMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
