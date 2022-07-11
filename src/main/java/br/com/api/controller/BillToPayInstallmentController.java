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

import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.entity.repository.BillToPayInstallmentFilter;
import br.com.api.flow.billtopayinstallment.SearchBillToPayInstallmentByFilterFlow;
import br.com.api.flow.billtopayinstallment.UpdateBillToPayInstallmentFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-pay-installment")
@CrossOrigin
public class BillToPayInstallmentController {

	@Autowired
	private UpdateBillToPayInstallmentFlow updateBillToPayInstallmentFlow;
	@Autowired
	private SearchBillToPayInstallmentByFilterFlow findBillToPayInstallmentByFilterFlow;

	@PutMapping
	public ResponseEntity<ResponseAPI<BillToPayInstallmentDTO>> update(@RequestBody BillToPayInstallmentDTO billToPayDTO,
			@RequestHeader HttpHeaders headers) {

		return updateBillToPayInstallmentFlow.execute(billToPayDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<BillToPayInstallmentFilter>> find(@RequestBody BillToPayInstallmentFilter filter,
			@RequestHeader HttpHeaders headers) {

		return findBillToPayInstallmentByFilterFlow.execute(filter, headers);
	}
}
