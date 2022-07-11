package br.com.api.flow.order.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.OrderMapper;
import br.com.api.dto.OrderDTO;
import br.com.api.entity.repository.OrderRepository;

@Component
public class UpdateOrderFlowItem {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderMapper orderMapper;

	public OrderDTO update(OrderDTO order) {

		return orderMapper.toDTO(orderRepository.save(orderMapper.toEntity(order)));
	}
}
