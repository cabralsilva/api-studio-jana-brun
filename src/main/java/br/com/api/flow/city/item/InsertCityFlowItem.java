package br.com.api.flow.city.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.CityMapper;
import br.com.api.dto.CityDTO;
import br.com.api.entity.repository.CityRepository;

@Component
public class InsertCityFlowItem {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private UpdateCityFlowItem updateCityFlowItem;

	@Autowired
	private CityMapper cityMapper;

	public CityDTO insert(@NonNull CityDTO city) {

		if (Objects.nonNull(city.getIdentifier())) {
			return updateCityFlowItem.update(city);
		}

		return cityMapper.toDTO(cityRepository.save(cityMapper.toEntity(city)));
	}
}
