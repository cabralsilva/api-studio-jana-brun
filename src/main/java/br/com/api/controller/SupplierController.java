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

import br.com.api.dto.SupplierDTO;
import br.com.api.entity.repository.SupplierFilter;
import br.com.api.flow.supplier.DeleteSupplierByIdentifierFlow;
import br.com.api.flow.supplier.FindSupplierByFilterFlow;
import br.com.api.flow.supplier.InsertSupplierFlow;
import br.com.api.flow.supplier.UpdateSupplierFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/supplier")
@CrossOrigin
public class SupplierController {

	@Autowired
	private InsertSupplierFlow insertSupplierFlow;
	@Autowired
	private UpdateSupplierFlow updateSupplierFlow;
	@Autowired
	private FindSupplierByFilterFlow findSupplierByFilterFlow;
	@Autowired
	private DeleteSupplierByIdentifierFlow deleteSupplierByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<SupplierDTO>> insert(@RequestBody SupplierDTO supplierDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertSupplierFlow.execute(supplierDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<SupplierDTO>> update(@RequestBody SupplierDTO supplierDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateSupplierFlow.execute(supplierDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<SupplierFilter>> find(@RequestBody SupplierFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findSupplierByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteSupplierByIdentifierFlow.execute(identifier, headers));
	}

}
