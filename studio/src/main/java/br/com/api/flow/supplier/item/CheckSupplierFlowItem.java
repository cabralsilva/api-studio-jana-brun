package br.com.api.flow.supplier.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.SupplierDTO;
import br.com.api.entity.repository.SupplierFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckSupplierFlowItem {

	@Autowired
	private FindSupplierByFilterFlowItem findSupplierByFilterFlowItem;

	public SupplierDTO checkIfExist(SupplierDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new SupplierFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findSupplierByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
