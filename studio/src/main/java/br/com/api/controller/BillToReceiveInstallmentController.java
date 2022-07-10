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

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentFilter;
import br.com.api.flow.billtoreceiveinstallment.FindBillToReceiveInstallmentByFilterFlow;
import br.com.api.flow.billtoreceiveinstallment.UpdateBillToReceiveInstallmentFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-receive-installment")
@CrossOrigin
public class BillToReceiveInstallmentController {

	@Autowired
	private UpdateBillToReceiveInstallmentFlow updateBillToReceiveInstallmentFlow;
	@Autowired
	private FindBillToReceiveInstallmentByFilterFlow findBillToReceiveInstallmentByFilterFlow;

	@PutMapping
	public ResponseEntity<ResponseAPI<BillToReceiveInstallmentDTO>> update(
			@RequestBody BillToReceiveInstallmentDTO billToReceiveDTO, @RequestHeader HttpHeaders headers) {

		return updateBillToReceiveInstallmentFlow.execute(billToReceiveDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<BillToReceiveInstallmentFilter>> find(
			@RequestBody BillToReceiveInstallmentFilter filter, @RequestHeader HttpHeaders headers) {

		return findBillToReceiveInstallmentByFilterFlow.execute(filter, headers);
	}
}
