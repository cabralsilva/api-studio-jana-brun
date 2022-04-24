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

import br.com.api.dto.StudentDTO;
import br.com.api.entity.repository.StudentFilter;
import br.com.api.flow.student.DeleteStudentByIdentifierFlow;
import br.com.api.flow.student.FindStudentByFilterFlow;
import br.com.api.flow.student.InsertStudentFlow;
import br.com.api.flow.student.UpdateStudentFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

	@Autowired
	private InsertStudentFlow insertStudentFlow;
	@Autowired
	private UpdateStudentFlow updateStudentFlow;
	@Autowired
	private FindStudentByFilterFlow findStudentByFilterFlow;
	@Autowired
	private DeleteStudentByIdentifierFlow deleteStudentByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody StudentDTO studentDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertStudentFlow.execute(studentDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody StudentDTO studentDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateStudentFlow.execute(studentDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI> find(@RequestBody StudentFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findStudentByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteStudentByIdentifierFlow.execute(identifier, headers));
	}

}
