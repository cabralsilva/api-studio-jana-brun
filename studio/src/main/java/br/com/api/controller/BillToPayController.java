package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.BillToPayDTO;
import br.com.api.entity.repository.BillToPayFilter;
import br.com.api.flow.billtopay.DeleteBillToPayByIdentifierFlow;
import br.com.api.flow.billtopay.FindBillToPayByFilterFlow;
import br.com.api.flow.billtopay.InsertBillToPayFlow;
import br.com.api.flow.billtopay.SetPaymentConditionToBillToPayFlow;
import br.com.api.flow.billtopay.UpdateBillToPayFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-pay")
public class BillToPayController {

	@Autowired
	private InsertBillToPayFlow insertBillToPayFlow;
	@Autowired
	private UpdateBillToPayFlow updateBillToPayFlow;
	@Autowired
	private FindBillToPayByFilterFlow findBillToPayByFilterFlow;
	@Autowired
	private DeleteBillToPayByIdentifierFlow deleteBillToPayByIdentifierFlow;
	@Autowired
	private SetPaymentConditionToBillToPayFlow setPaymentConditionToBillToPayFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody BillToPayDTO billToPayDTO,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertBillToPayFlow.execute(billToPayDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody BillToPayDTO billToPayDTO,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateBillToPayFlow.execute(billToPayDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI> find(@RequestBody BillToPayFilter filter,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findBillToPayByFilterFlow.execute(filter, headers));
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteBillToPayByIdentifierFlow.execute(identifier, headers));
	}

	@PutMapping("/set-payment-condition/{identifier}/{paymentConditionIdentifier}")
	public ResponseEntity<ResponseAPI> setPaymentCondition(@PathVariable Integer identifier,
			@PathVariable Integer paymentConditionIdentifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity
				.ok(setPaymentConditionToBillToPayFlow.execute(identifier, paymentConditionIdentifier, headers));
	}
}
