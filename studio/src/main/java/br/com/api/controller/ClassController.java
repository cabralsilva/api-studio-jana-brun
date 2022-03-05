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

import br.com.api.dto.ClassDTO;
import br.com.api.entity.repository.ClassFilter;
import br.com.api.flow.classes.DeleteClassByIdentifierFlow;
import br.com.api.flow.classes.FindClassByFilterFlow;
import br.com.api.flow.classes.InsertClassFlow;
import br.com.api.flow.classes.UpdateClassFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/class")
public class ClassController {

	@Autowired
	private InsertClassFlow insertClassFlow;
	@Autowired
	private UpdateClassFlow updateClassFlow;
	@Autowired
	private FindClassByFilterFlow findClassByFilterFlow;
	@Autowired
	private DeleteClassByIdentifierFlow deleteClassByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody ClassDTO classDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertClassFlow.execute(classDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody ClassDTO classDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateClassFlow.execute(classDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody ClassFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findClassByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteClassByIdentifierFlow.execute(identifier, headers));
	}

}
