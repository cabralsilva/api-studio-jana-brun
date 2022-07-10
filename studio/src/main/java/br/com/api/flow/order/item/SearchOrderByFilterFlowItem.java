package br.com.api.flow.order.item;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.OrderMapper;
import br.com.api.entity.Order;
import br.com.api.entity.repository.OrderFilter;
import br.com.api.entity.repository.OrderRepository;
import br.com.api.entity.repository.OrderRepositoryImpl;
import lombok.var;

@Component
public class SearchOrderByFilterFlowItem {

	@Autowired
	private OrderRepositoryImpl orderRepositoryImpl;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderMapper orderMapper;

	public OrderFilter findByFilter(OrderFilter filter) {

		Example<Order> example = Example.of(orderMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = orderRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> orderMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = orderRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> orderMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	public OrderFilter findByFilterImpl(OrderFilter filter) throws NoSuchMethodException, SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = orderRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> orderMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = orderRepositoryImpl.findByFilter(filter);
		filter.setResult(ret.stream().map(p -> orderMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
