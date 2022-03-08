package br.com.api.flow.registeritem.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.RegisterItemDTO;
import br.com.api.entity.repository.RegisterItemFilter;
import br.com.api.exceptions.FindByFilterException;

@Service
public class CheckRegisterItemFlowItem {

	@Autowired
	private FindRegisterItemByFilterFlowItem findRegisterItemByFilterFlowItem;

	public RegisterItemDTO checkIfExist(RegisterItemDTO registerItemDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new RegisterItemFilter();
		filter.setExample(registerItemDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findRegisterItemByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			registerItemDTO.setIdentifier(existing.get().getIdentifier());
		}
		return registerItemDTO;
	}
}
