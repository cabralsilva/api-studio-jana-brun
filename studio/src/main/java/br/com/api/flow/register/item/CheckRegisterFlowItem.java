package br.com.api.flow.register.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterDTO;
import br.com.api.entity.repository.RegisterFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckRegisterFlowItem {

	@Autowired
	private FindRegisterByFilterFlowItem findRegisterByFilterFlowItem;

	public RegisterDTO checkIfExist(RegisterDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new RegisterFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findRegisterByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
