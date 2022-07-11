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

import br.com.api.dto.GrateItemDTO;
import br.com.api.entity.repository.GrateItemFilter;
import br.com.api.flow.grateitem.DeleteGrateItemByIdentifierFlow;
import br.com.api.flow.grateitem.FindGrateItemByFilterFlow;
import br.com.api.flow.grateitem.InsertGrateItemFlow;
import br.com.api.flow.grateitem.UpdateGrateItemFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/grate-item")
@CrossOrigin
public class GrateItemController {

	@Autowired
	private InsertGrateItemFlow insertGrateItemFlow;
	@Autowired
	private UpdateGrateItemFlow updateGrateItemFlow;
	@Autowired
	private FindGrateItemByFilterFlow findGrateItemByFilterFlow;
	@Autowired
	private DeleteGrateItemByIdentifierFlow deleteGrateItemByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<GrateItemDTO>> insert(@RequestBody GrateItemDTO grateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertGrateItemFlow.execute(grateDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<GrateItemDTO>> update(@RequestBody GrateItemDTO grateDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateGrateItemFlow.execute(grateDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<GrateItemFilter>> find(@RequestBody GrateItemFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findGrateItemByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteGrateItemByIdentifierFlow.execute(identifier, headers));
	}

}
