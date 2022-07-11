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

import br.com.api.dto.PriceTableDTO;
import br.com.api.entity.repository.PriceTableFilter;
import br.com.api.flow.pricetable.FindPriceTableByFilterFlow;
import br.com.api.flow.pricetable.InsertPriceTableFlow;
import br.com.api.flow.pricetable.UpdatePriceTableFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/price-table")
@CrossOrigin
public class PriceTableController {

	@Autowired
	private InsertPriceTableFlow insertPriceTableFlow;
	@Autowired
	private UpdatePriceTableFlow updatePriceTableFlow;
	@Autowired
	private FindPriceTableByFilterFlow findPriceTableByFilterFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<PriceTableDTO>> insert(@RequestBody PriceTableDTO priceTableDTO,
			@RequestHeader HttpHeaders headers) {

		return insertPriceTableFlow.execute(priceTableDTO, headers);
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<PriceTableDTO>> update(@RequestBody PriceTableDTO priceTableDTO,
			@RequestHeader HttpHeaders headers) {

		return updatePriceTableFlow.execute(priceTableDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<PriceTableFilter>> find(@RequestBody PriceTableFilter filter,
			@RequestHeader HttpHeaders headers) {

		return findPriceTableByFilterFlow.execute(filter, headers);
	}
//
//	@DeleteMapping("/{identifier}")
//	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {
//
//		return deletePriceTableByIdentifierFlow.execute(identifier, headers);
//	}
}
