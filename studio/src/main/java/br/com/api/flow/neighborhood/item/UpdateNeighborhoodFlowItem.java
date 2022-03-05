package br.com.api.flow.neighborhood.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.CountryMapper;
import br.com.api.dto.CountryDTO;
import br.com.api.entity.repository.CountryRepository;

@Component
public class UpdateNeighborhoodFlowItem {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CountryMapper countryMapper;

	public CountryDTO update(CountryDTO country) {

		return countryMapper.toDTO(countryRepository.save(countryMapper.toEntity(country)));
	}
}
