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

import br.com.api.dto.CountryDTO;
import br.com.api.entity.repository.CountryFilter;
import br.com.api.flow.country.DeleteCountryByIdentifierFlow;
import br.com.api.flow.country.FindCountryByFilterFlow;
import br.com.api.flow.country.InsertCountryFlow;
import br.com.api.flow.country.UpdateCountryFlow;
import br.com.api.utils.ResponseAPI;

@RestController
@RequestMapping(path = "/country")
public class CountryController {

	@Autowired
	private InsertCountryFlow insertCountryFlow;
	@Autowired
	private UpdateCountryFlow updateCountryFlow;
	@Autowired
	private FindCountryByFilterFlow findCountryByFilterFlow;
	@Autowired
	private DeleteCountryByIdentifierFlow deleteCountryByIdentifierFlow;

	@PostMapping
	public ResponseEntity<ResponseAPI> insert(@RequestBody CountryDTO countryDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(insertCountryFlow.execute(countryDTO, headers));
	}

	@PutMapping
	public ResponseEntity<ResponseAPI> update(@RequestBody CountryDTO countryDTO, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(updateCountryFlow.execute(countryDTO, headers));
	}

	@PostMapping("/find")
	public ResponseEntity<ResponseAPI> find(@RequestBody CountryFilter filter, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(findCountryByFilterFlow.execute(filter, headers));
	}
	
	@DeleteMapping("/{identifier}")
	public ResponseEntity<ResponseAPI> delete(@PathVariable Integer identifier, @RequestHeader HttpHeaders headers) {

		return ResponseEntity.ok(deleteCountryByIdentifierFlow.execute(identifier, headers));
	}

}
