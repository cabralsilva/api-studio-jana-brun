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

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.flow.billtoreceive.DeleteBillToReceiveByIdentifierFlow;
import br.com.api.flow.billtoreceive.FindBillToReceiveByFilterFlow;
import br.com.api.flow.billtoreceive.InsertBillToReceiveFlow;
import br.com.api.flow.billtoreceive.SetPaymentConditionToBillToReceiveFlow;
import br.com.api.flow.billtoreceive.UpdateBillToReceiveFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-receive")
public class BillToReceiveController {

	@Autowired
	private InsertBillToReceiveFlow insertBillToReceiveFlow;
	@Autowired
	private UpdateBillToReceiveFlow updateBillToReceiveFlow;
	@Autowired
	private FindBillToReceiveByFilterFlow findBillToReceiveByFilterFlow;
	@Autowired
	private DeleteBillToReceiveByIdentifierFlow deleteBillToReceiveByIdentifierFlow;
	@Autowired
	private SetPaymentConditionToBillToReceiveFlow setPaymentConditionToBillToReceiveFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody BillToReceiveDTO billToReceiveDTO,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertBillToReceiveFlow.execute(billToReceiveDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody BillToReceiveDTO billToReceiveDTO,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateBillToReceiveFlow.execute(billToReceiveDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI> find(@RequestBody BillToReceiveFilter filter,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findBillToReceiveByFilterFlow.execute(filter, headers));
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteBillToReceiveByIdentifierFlow.execute(identifier, headers));
	}

	@PutMapping("/set-payment-condition/{identifier}/{paymentConditionIdentifier}")
	public ResponseEntity<ResponseAPI> setPaymentCondition(@PathVariable Integer identifier,
			@PathVariable Integer paymentConditionIdentifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity
				.ok(setPaymentConditionToBillToReceiveFlow.execute(identifier, paymentConditionIdentifier, headers));
	}
}
