package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.flow.billtoreceiveinstallmentpayment.InsertBillToReceiveInstallmentPaymentFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-receive-installment-payment")
@CrossOrigin
public class BillToReceiveInstallmentPaymentController {

	@Autowired
	private InsertBillToReceiveInstallmentPaymentFlow insertBillToReceiveInstallmentPaymentFlowItem;

	@PostMapping
	public ResponseEntity<ResponseAPI<BillToReceiveInstallmentPaymentDTO>> insert(
			@RequestBody BillToReceiveInstallmentPaymentDTO payment, @RequestHeader HttpHeaders headers) {

		return insertBillToReceiveInstallmentPaymentFlowItem.execute(payment, headers);
	}
}
