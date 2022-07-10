package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.OrderDTO;
import br.com.api.entity.repository.OrderFilter;
import br.com.api.flow.order.InsertOrderFlow;
import br.com.api.flow.order.SearchOrderByFilterFlow;
import br.com.api.flow.order.UpdateOrderFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/order")
@CrossOrigin
public class OrderController {

	@Autowired
	private InsertOrderFlow insertOrderFlow;
	@Autowired
	private UpdateOrderFlow updateOrderFlow;
	@Autowired
	private SearchOrderByFilterFlow searchOrderByFilter;

	@PostMapping
	public ResponseEntity<ResponseAPI<OrderDTO>> insert(@RequestBody OrderDTO orderDTO,
			@RequestHeader HttpHeaders headers) {

		return insertOrderFlow.execute(orderDTO, headers);
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<OrderDTO>> update(@RequestBody OrderDTO orderDTO,
			@RequestHeader HttpHeaders headers) {

		return updateOrderFlow.execute(orderDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<OrderFilter>> find(@RequestBody OrderFilter filter,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(searchOrderByFilter.execute(filter, headers));
	}
}
