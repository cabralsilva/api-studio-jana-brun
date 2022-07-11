package br.com.api.flow.order.item;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.OrderMapper;
import br.com.api.dto.BillToReceiveDTO;
import br.com.api.dto.OrderDTO;
import br.com.api.entity.repository.OrderRepository;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.billtoreceive.item.InsertBillToReceiveFlowItem;

@Component
public class InsertOrderFlowItem {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UpdateOrderFlowItem updateOrderFlowItem;

	@Autowired
	private InsertBillToReceiveFlowItem insertBillToReceiveFlowItem;

	@Autowired
	private OrderMapper orderMapper;

	public OrderDTO insert(@NonNull OrderDTO orderDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		if (Objects.nonNull(orderDTO.getIdentifier())) {
			return updateOrderFlowItem.update(orderDTO);
		}

		OrderDTO orderDTOAux = orderMapper.toDTO(orderRepository.save(orderMapper.toEntity(orderDTO)));

		for (BillToReceiveDTO billToReceive : orderDTO.getBillToReceiveList()) {
			billToReceive.setOrder(OrderDTO.builder().identifier(orderDTOAux.getIdentifier()).build());
			billToReceive.setTitle("SALE".concat(String.valueOf(orderDTOAux.getIdentifier())).concat("-")
					.concat(String.valueOf(orderDTO.getBillToReceiveList().indexOf(billToReceive) + 1)));
			insertBillToReceiveFlowItem.insert(billToReceive);
		}
		return orderDTOAux;
	}
}
