package br.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.flow.paymentcondition.DeletePaymentConditionByIdentifierFlow;
import br.com.api.flow.paymentcondition.FindPaymentConditionByFilterFlow;
import br.com.api.flow.paymentcondition.InsertPaymentConditionFlow;
import br.com.api.flow.paymentcondition.UpdatePaymentConditionFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/payment-condition")
@CrossOrigin
public class PaymentConditionController {

	@Autowired
	private InsertPaymentConditionFlow insertPaymentConditionFlow;
	@Autowired
	private UpdatePaymentConditionFlow updatePaymentConditionFlow;
	@Autowired
	private FindPaymentConditionByFilterFlow findPaymentConditionByFilterFlow;
	@Autowired
	private DeletePaymentConditionByIdentifierFlow deletePaymentConditionByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<PaymentConditionDTO>> insert(@RequestBody PaymentConditionDTO paymentConditionDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertPaymentConditionFlow.execute(paymentConditionDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<PaymentConditionDTO>> update(@RequestBody PaymentConditionDTO paymentConditionDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updatePaymentConditionFlow.execute(paymentConditionDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<PaymentConditionFilter>> find(@RequestBody PaymentConditionFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findPaymentConditionByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deletePaymentConditionByIdentifierFlow.execute(identifier, headers));
	}

}
