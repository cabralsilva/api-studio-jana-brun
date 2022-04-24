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

import br.com.api.dto.PaymentMethodDTO;
import br.com.api.entity.repository.PaymentMethodFilter;
import br.com.api.flow.paymentmethod.DeletePaymentMethodByIdentifierFlow;
import br.com.api.flow.paymentmethod.FindPaymentMethodByFilterFlow;
import br.com.api.flow.paymentmethod.InsertPaymentMethodFlow;
import br.com.api.flow.paymentmethod.UpdatePaymentMethodFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/payment-method")
@CrossOrigin
public class PaymentMethodController {

	@Autowired
	private InsertPaymentMethodFlow insertPaymentMethodFlow;
	@Autowired
	private UpdatePaymentMethodFlow updatePaymentMethodFlow;
	@Autowired
	private FindPaymentMethodByFilterFlow findPaymentMethodByFilterFlow;
	@Autowired
	private DeletePaymentMethodByIdentifierFlow deletePaymentMethodByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<PaymentMethodDTO>> insert(@RequestBody PaymentMethodDTO paymentMethodDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertPaymentMethodFlow.execute(paymentMethodDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<PaymentMethodDTO>> update(@RequestBody PaymentMethodDTO paymentMethodDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updatePaymentMethodFlow.execute(paymentMethodDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<PaymentMethodFilter>> find(@RequestBody PaymentMethodFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findPaymentMethodByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deletePaymentMethodByIdentifierFlow.execute(identifier, headers));
	}

}
