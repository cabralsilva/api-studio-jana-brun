package br.com.api.flow.city.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.CityMapper;
import br.com.api.dto.CityDTO;
import br.com.api.entity.repository.CityRepository;

@Component
public class UpdateCityFlowItem {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityMapper cityMapper;

	public CityDTO update(CityDTO city) {

		return cityMapper.toDTO(cityRepository.save(cityMapper.toEntity(city)));
	}
}
