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

import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.repository.RegisterItemFilter;
import br.com.api.flow.registeritem.DeleteRegisterItemByIdentifierFlow;
import br.com.api.flow.registeritem.FindRegisterItemByFilterFlow;
import br.com.api.flow.registeritem.InsertRegisterItemFlow;
import br.com.api.flow.registeritem.UpdateRegisterItemFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/register-item")
public class RegisterItemController {

	@Autowired
	private InsertRegisterItemFlow insertRegisterItemFlow;
	@Autowired
	private UpdateRegisterItemFlow updateRegisterItemFlow;
	@Autowired
	private FindRegisterItemByFilterFlow findRegisterItemByFilterFlow;
	@Autowired
	private DeleteRegisterItemByIdentifierFlow deleteRegisterItemByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody RegisterItemDTO registerItemDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertRegisterItemFlow.execute(registerItemDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody RegisterItemDTO registerItemDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateRegisterItemFlow.execute(registerItemDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody RegisterItemFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findRegisterItemByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteRegisterItemByIdentifierFlow.execute(identifier, headers));
	}

}
