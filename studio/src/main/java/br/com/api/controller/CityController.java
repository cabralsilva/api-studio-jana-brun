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

import br.com.api.dto.CityDTO;
import br.com.api.entity.repository.CityFilter;
import br.com.api.flow.city.DeleteCityByIdentifierFlow;
import br.com.api.flow.city.FindCityByFilterFlow;
import br.com.api.flow.city.InsertCityFlow;
import br.com.api.flow.city.UpdateCityFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/city")
@CrossOrigin
public class CityController {

	@Autowired
	private InsertCityFlow insertCityFlow;
	@Autowired
	private UpdateCityFlow updateCityFlow;
	@Autowired
	private FindCityByFilterFlow findCityByFilterFlow;
	@Autowired
	private DeleteCityByIdentifierFlow deleteCityByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody CityDTO cityDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertCityFlow.execute(cityDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody CityDTO cityDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateCityFlow.execute(cityDTO, headers));
	}

	@PostMapping("/search")
	public ResponseEntity<ResponseAPI<CityFilter>> find(@RequestBody CityFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findCityByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteCityByIdentifierFlow.execute(identifier, headers));
	}

}
