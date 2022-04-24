package br.com.api.flow.product.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.ProductDTO;
import br.com.api.entity.repository.ProductFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckProductFlowItem {

	@Autowired
	private FindProductByFilterFlowItem findProductByFilterFlowItem;

	public ProductDTO checkIfExist(ProductDTO countryDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new ProductFilter();
		filter.setExample(countryDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findProductByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			countryDTO.setIdentifier(existing.get().getIdentifier());
		}
		return countryDTO;
	}
}
