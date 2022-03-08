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

import br.com.api.dto.GrateDTO;
import br.com.api.entity.repository.GrateFilter;
import br.com.api.flow.grate.DeleteGrateByIdentifierFlow;
import br.com.api.flow.grate.FindGrateByFilterFlow;
import br.com.api.flow.grate.InsertGrateFlow;
import br.com.api.flow.grate.UpdateGrateFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/grate")
public class GrateController {

	@Autowired
	private InsertGrateFlow insertGrateFlow;
	@Autowired
	private UpdateGrateFlow updateGrateFlow;
	@Autowired
	private FindGrateByFilterFlow findGrateByFilterFlow;
	@Autowired
	private DeleteGrateByIdentifierFlow deleteGrateByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody GrateDTO grateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertGrateFlow.execute(grateDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody GrateDTO grateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateGrateFlow.execute(grateDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody GrateFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findGrateByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteGrateByIdentifierFlow.execute(identifier, headers));
	}

}
