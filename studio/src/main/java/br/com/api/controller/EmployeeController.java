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

import br.com.api.dto.EmployeeDTO;
import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.flow.employee.DeleteEmployeeByIdentifierFlow;
import br.com.api.flow.employee.FindEmployeeByFilterFlow;
import br.com.api.flow.employee.InsertEmployeeFlow;
import br.com.api.flow.employee.UpdateEmployeeFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/employee")
@CrossOrigin
public class EmployeeController {

	@Autowired
	private InsertEmployeeFlow insertEmployeeFlow;
	@Autowired
	private UpdateEmployeeFlow updateEmployeeFlow;
	@Autowired
	private FindEmployeeByFilterFlow findEmployeeByFilterFlow;
	@Autowired
	private DeleteEmployeeByIdentifierFlow deleteEmployeeByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI<EmployeeDTO>> insert(@RequestBody EmployeeDTO employeeDTO,
			@RequestHeader HttpHeaders headers) throws Exception {

		return ResponseEntity.ok(insertEmployeeFlow.execute(employeeDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI<EmployeeDTO>> update(@RequestBody EmployeeDTO employeeDTO,
			@RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateEmployeeFlow.execute(employeeDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<EmployeeFilter>> find(@RequestBody EmployeeFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findEmployeeByFilterFlow.execute(filter, headers));
	}

	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI<Void>> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteEmployeeByIdentifierFlow.execute(identifier, headers));
	}

}
