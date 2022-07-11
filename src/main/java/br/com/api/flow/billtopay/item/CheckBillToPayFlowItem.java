package br.com.api.flow.billtopay.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToPayDTO;
import br.com.api.entity.repository.BillToPayFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToPayFlowItem {

	@Autowired
	private FindBillToPayByFilterFlowItem findBillToPayByFilterFlowItem;

	public BillToPayDTO checkIfExist(BillToPayDTO billToPayDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToPayFilter();
		filter.setExample(billToPayDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findBillToPayByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			billToPayDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToPayDTO;
	}
}
