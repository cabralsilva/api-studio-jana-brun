package br.com.api.flow.state.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.CountryMapper;
import br.com.api.dto.CountryDTO;
import br.com.api.entity.repository.CountryRepository;

@Component
public class InsertStateFlowItem {

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private UpdateStateFlowItem updateCountryFlowItem;

	@Autowired
	private CountryMapper countryMapper;

	public CountryDTO insert(@NonNull CountryDTO country) {

		if (Objects.nonNull(country.getIdentifier())) {
			return updateCountryFlowItem.update(country);
		}

		return countryMapper.toDTO(countryRepository.save(countryMapper.toEntity(country)));
	}
}
