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

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.flow.billtoreceive.DeleteBillToReceiveByIdentifierFlow;
import br.com.api.flow.billtoreceive.FindBillToReceiveByFilterFlow;
import br.com.api.flow.billtoreceive.InsertBillToReceiveFlow;
import br.com.api.flow.billtoreceive.UpdateBillToReceiveFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/bill-to-receive")
@CrossOrigin
public class BillToReceiveController {

	@Autowired
	private InsertBillToReceiveFlow insertBillToReceiveFlow;
	@Autowired
	private UpdateBillToReceiveFlow updateBillToReceiveFlow;
	@Autowired
	private FindBillToReceiveByFilterFlow findBillToReceiveByFilterFlow;
	@Autowired
	private DeleteBillToReceiveByIdentifierFlow deleteBillToReceiveByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<BillToReceiveDTO>> insert(@RequestBody BillToReceiveDTO billToReceiveDTO,
			@RequestHeader HttpHeaders headers) {

		return insertBillToReceiveFlow.execute(billToReceiveDTO, headers);
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<BillToReceiveDTO>> update(@RequestBody BillToReceiveDTO billToReceiveDTO,
			@RequestHeader HttpHeaders headers) {

		return updateBillToReceiveFlow.execute(billToReceiveDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<BillToReceiveFilter>> find(@RequestBody BillToReceiveFilter filter,
			@RequestHeader HttpHeaders headers) {

		return findBillToReceiveByFilterFlow.execute(filter, headers);
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return deleteBillToReceiveByIdentifierFlow.execute(identifier, headers);
	}
}
