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

import br.com.api.dto.MatriculationItemDTO;
import br.com.api.entity.repository.MatriculationItemFilter;
import br.com.api.flow.matriculationitem.DeleteMatriculationItemByIdentifierFlow;
import br.com.api.flow.matriculationitem.FindMatriculationItemByFilterFlow;
import br.com.api.flow.matriculationitem.InsertMatriculationItemFlow;
import br.com.api.flow.matriculationitem.UpdateMatriculationItemFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/matriculation-item")
public class MatriculationItemController {

	@Autowired
	private InsertMatriculationItemFlow insertmatriculationItemFlow;
	@Autowired
	private UpdateMatriculationItemFlow updatematriculationItemFlow;
	@Autowired
	private FindMatriculationItemByFilterFlow findmatriculationItemByFilterFlow;
	@Autowired
	private DeleteMatriculationItemByIdentifierFlow deletematriculationItemByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody MatriculationItemDTO matriculationItemDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertmatriculationItemFlow.execute(matriculationItemDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody MatriculationItemDTO matriculationItemDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updatematriculationItemFlow.execute(matriculationItemDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI> find(@RequestBody MatriculationItemFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findmatriculationItemByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deletematriculationItemByIdentifierFlow.execute(identifier, headers));
	}

}
