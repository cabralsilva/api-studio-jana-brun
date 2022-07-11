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

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.dto.PriceTableItemDTO;
import br.com.api.flow.pricetableitem.GetPriceTableItemByCartItemFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/price-table-item")
@CrossOrigin
public class PriceTableItemController {

	@Autowired
	private GetPriceTableItemByCartItemFlow getPriceTableItemByCartItemFlow;

	@PostMapping("/get-by-cart-item")
	public ResponseEntity<ResponseAPI<PriceTableItemDTO>> getByCartItem(@RequestBody MatriculationItemDTO matriculationItemDTO,
			@RequestHeader HttpHeaders headers) {

		return getPriceTableItemByCartItemFlow.execute(matriculationItemDTO, headers);
	}
}
