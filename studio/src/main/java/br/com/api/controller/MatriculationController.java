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

import br.com.api.dto.MatriculationDTO;
import br.com.api.entity.repository.MatriculationFilter;
import br.com.api.flow.matriculation.DeleteMatriculationByIdentifierFlow;
import br.com.api.flow.matriculation.EffectiveMatriculationFlow;
import br.com.api.flow.matriculation.FindMatriculationByFilterFlow;
import br.com.api.flow.matriculation.InsertMatriculationFlow;
import br.com.api.flow.matriculation.UpdateMatriculationFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/matriculation")
@CrossOrigin
public class MatriculationController {

	@Autowired
	private InsertMatriculationFlow insertMatriculationFlow;
	@Autowired
	private UpdateMatriculationFlow updateMatriculationFlow;
	@Autowired
	private FindMatriculationByFilterFlow findMatriculationByFilterFlow;
	@Autowired
	private DeleteMatriculationByIdentifierFlow deleteMatriculationByIdentifierFlow;
	@Autowired
	private EffectiveMatriculationFlow effectiveMatriculationFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<MatriculationDTO>> insert(@RequestBody MatriculationDTO matriculationDTO,
			@RequestHeader HttpHeaders headers) {

		return insertMatriculationFlow.execute(matriculationDTO, headers);
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<MatriculationDTO>> update(@RequestBody MatriculationDTO matriculationDTO,
			@RequestHeader HttpHeaders headers) {

		return updateMatriculationFlow.execute(matriculationDTO, headers);
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<MatriculationFilter>> find(@RequestBody MatriculationFilter filter,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findMatriculationByFilterFlow.execute(filter, headers));
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteMatriculationByIdentifierFlow.execute(identifier, headers));
	}

	@PutMapping("/effective/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> effective(@PathVariable Integer identifier,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(effectiveMatriculationFlow.execute(identifier));
	}

}
