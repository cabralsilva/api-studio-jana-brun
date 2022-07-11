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

import br.com.api.dto.ClassroomDTO;
import br.com.api.entity.repository.ClassroomFilter;
import br.com.api.flow.classroom.DeleteClassroomByIdentifierFlow;
import br.com.api.flow.classroom.FindClassroomByFilterFlow;
import br.com.api.flow.classroom.InsertClassroomFlow;
import br.com.api.flow.classroom.UpdateClassroomFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/classroom")
@CrossOrigin
public class ClassroomController {

	@Autowired
	private InsertClassroomFlow insertClassroomFlow;
	@Autowired
	private UpdateClassroomFlow updateClassroomFlow;
	@Autowired
	private FindClassroomByFilterFlow findClassroomByFilterFlow;
	@Autowired
	private DeleteClassroomByIdentifierFlow deleteClassroomByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<ClassroomDTO>> insert(@RequestBody ClassroomDTO classroomDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertClassroomFlow.execute(classroomDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<ClassroomDTO>> update(@RequestBody ClassroomDTO classroomDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateClassroomFlow.execute(classroomDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<ClassroomFilter>> find(@RequestBody ClassroomFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findClassroomByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteClassroomByIdentifierFlow.execute(identifier, headers));
	}

}
