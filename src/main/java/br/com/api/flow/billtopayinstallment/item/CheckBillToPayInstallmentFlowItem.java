package br.com.api.flow.billtopayinstallment.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.entity.repository.BillToPayInstallmentFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToPayInstallmentFlowItem {

	@Autowired
	private SearchBillToPayInstallmentByFilterFlowItem findBillToPayInstallmentByFilterFlowItem;

	public BillToPayInstallmentDTO checkIfExist(BillToPayInstallmentDTO billToPayInstallmentDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToPayInstallmentFilter();
		filter.setExample(billToPayInstallmentDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findBillToPayInstallmentByFilterFlowItem.search(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			billToPayInstallmentDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToPayInstallmentDTO;
	}
}
