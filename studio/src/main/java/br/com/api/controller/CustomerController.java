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

import br.com.api.dto.PersonDTO;
import br.com.api.entity.repository.PersonFilter;
import br.com.api.flow.person.DeletePersonByIdentifierFlow;
import br.com.api.flow.person.FindPersonByFilterFlow;
import br.com.api.flow.person.InsertPersonFlow;
import br.com.api.flow.person.UpdatePersonFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/customer")
@CrossOrigin
public class CustomerController {

	@Autowired
	private InsertPersonFlow insertPersonFlow;
	@Autowired
	private UpdatePersonFlow updatePersonFlow;
	@Autowired
	private FindPersonByFilterFlow findPersonByFilterFlow;
	@Autowired
	private DeletePersonByIdentifierFlow deletePersonByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<PersonDTO>> insert(@RequestBody PersonDTO supplierDTO,
			@RequestHeader HttpHeaders headers) throws Exception {

		return ResponseEntity.ok(insertPersonFlow.execute(supplierDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<PersonDTO>> update(@RequestBody PersonDTO supplierDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updatePersonFlow.execute(supplierDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<PersonFilter>> find(@RequestBody PersonFilter filter, @RequestHeader HttpHeaders headers) {

		return findPersonByFilterFlow.execute(filter, headers);
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deletePersonByIdentifierFlow.execute(identifier, headers));
	}

}
