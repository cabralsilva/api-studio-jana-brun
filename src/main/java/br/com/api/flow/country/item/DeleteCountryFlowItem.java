package br.com.api.flow.country.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.CountryRepository;

@Component
public class DeleteCountryFlowItem {

	@Autowired
	private CountryRepository countryRepository;

	public void delete(Integer identifier) {

		countryRepository.deleteById(identifier);
	}
}
