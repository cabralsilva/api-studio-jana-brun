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

import br.com.api.dto.ProductDTO;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.flow.product.DeleteProductByIdentifierFlow;
import br.com.api.flow.product.FindProductByFilterFlow;
import br.com.api.flow.product.InsertProductFlow;
import br.com.api.flow.product.UpdateProductFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

	@Autowired
	private InsertProductFlow insertProductFlow;
	@Autowired
	private UpdateProductFlow updateProductFlow;
	@Autowired
	private FindProductByFilterFlow findProductByFilterFlow;
	@Autowired
	private DeleteProductByIdentifierFlow deleteProductByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody ProductDTO productDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertProductFlow.execute(productDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody ProductDTO productDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateProductFlow.execute(productDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody ProductFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findProductByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteProductByIdentifierFlow.execute(identifier, headers));
	}

}
