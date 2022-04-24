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

import br.com.api.dto.StateDTO;
import br.com.api.entity.repository.StateFilter;
import br.com.api.flow.state.DeleteStateByIdentifierFlow;
import br.com.api.flow.state.FindStateByFilterFlow;
import br.com.api.flow.state.InsertStateFlow;
import br.com.api.flow.state.UpdateStateFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/state")
@CrossOrigin
public class StateController {

	@Autowired
	private InsertStateFlow insertStateFlow;
	@Autowired
	private UpdateStateFlow updateStateFlow;
	@Autowired
	private FindStateByFilterFlow findStateByFilterFlow;
	@Autowired
	private DeleteStateByIdentifierFlow deleteStateByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody StateDTO stateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertStateFlow.execute(stateDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody StateDTO stateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateStateFlow.execute(stateDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<StateFilter>> find(@RequestBody StateFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findStateByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteStateByIdentifierFlow.execute(identifier, headers));
	}

}
