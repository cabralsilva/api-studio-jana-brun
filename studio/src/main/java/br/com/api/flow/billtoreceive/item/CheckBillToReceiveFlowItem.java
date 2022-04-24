package br.com.api.flow.billtoreceive.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToReceiveFlowItem {

	@Autowired
	private FindBillToReceiveByFilterFlowItem findBillToReceiveByFilterFlowItem;

	public BillToReceiveDTO checkIfExist(BillToReceiveDTO billToReceiveDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToReceiveFilter();
		filter.setExample(billToReceiveDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findBillToReceiveByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			billToReceiveDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToReceiveDTO;
	}
}
