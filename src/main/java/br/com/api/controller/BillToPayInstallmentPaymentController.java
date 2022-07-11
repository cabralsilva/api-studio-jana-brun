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

import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.flow.billtopayinstallmentpayment.InsertBillToPayInstallmentPaymentFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-pay-installment-payment")
@CrossOrigin
public class BillToPayInstallmentPaymentController {

	@Autowired
	private InsertBillToPayInstallmentPaymentFlow insertBillToPayInstallmentPaymentFlowItem;

	@PostMapping
	public ResponseEntity<ResponseAPI<BillToPayInstallmentPaymentDTO>> update(
			@RequestBody BillToPayInstallmentPaymentDTO payment, @RequestHeader HttpHeaders headers) {

		return insertBillToPayInstallmentPaymentFlowItem.execute(payment, headers);
	}
}
