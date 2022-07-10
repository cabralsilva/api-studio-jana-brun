package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.PayrollDTO;
import br.com.api.entity.repository.PayrollFilter;
import br.com.api.flow.payroll.CheckoutPayrollFlow;
import br.com.api.flow.payroll.SearchPayrollByFilterFlow;
import br.com.api.flow.payroll.item.ProcessPayrollFlowItem;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/payroll")
@CrossOrigin
public class PayrollController {

	@Autowired
	private ProcessPayrollFlowItem generatePayrollFlowItem;

	@Autowired
	private SearchPayrollByFilterFlow searchPayrollByFilterFlow;

	@Autowired
	private CheckoutPayrollFlow checkoutPayrollFlow;

	@PostMapping("/process")
	public ResponseEntity<ResponseAPI<PayrollDTO>> find(@RequestBody PayrollDTO payroll,
			@RequestHeader HttpHeaders headers) throws SecurityException, IllegalArgumentException {

		return generatePayrollFlowItem.generate(payroll);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<PayrollFilter>> find(@RequestBody PayrollFilter filter,
			@RequestHeader HttpHeaders headers) {

		return searchPayrollByFilterFlow.execute(filter, headers);
	}

	@PatchMapping("/checkout/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> checkout(@PathVariable Integer identifier,
			@RequestHeader HttpHeaders headers) {

		return checkoutPayrollFlow.execute(identifier, headers);
	}
}
