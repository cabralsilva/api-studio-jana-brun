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

import br.com.api.dto.RegisterDTO;
import br.com.api.entity.repository.RegisterFilter;
import br.com.api.flow.register.DeleteRegisterByIdentifierFlow;
import br.com.api.flow.register.FindRegisterByFilterFlow;
import br.com.api.flow.register.InsertRegisterFlow;
import br.com.api.flow.register.UpdateRegisterFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/register")
public class RegisterController {

	@Autowired
	private InsertRegisterFlow insertRegisterFlow;
	@Autowired
	private UpdateRegisterFlow updateRegisterFlow;
	@Autowired
	private FindRegisterByFilterFlow findRegisterByFilterFlow;
	@Autowired
	private DeleteRegisterByIdentifierFlow deleteRegisterByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody RegisterDTO registerDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertRegisterFlow.execute(registerDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody RegisterDTO registerDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateRegisterFlow.execute(registerDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody RegisterFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findRegisterByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteRegisterByIdentifierFlow.execute(identifier, headers));
	}

}
