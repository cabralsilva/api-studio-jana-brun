package br.com.api.flow.billtoreceiveinstallment.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToReceiveInstallmentFlowItem {

	@Autowired
	private FindBillToReceiveInstallmentByFilterFlowItem findBillToReceiveInstallmentByFilterFlowItem;

	public BillToReceiveInstallmentDTO checkIfExist(BillToReceiveInstallmentDTO billToReceiveInstallmentDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToReceiveInstallmentFilter();
		filter.setExample(billToReceiveInstallmentDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findBillToReceiveInstallmentByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			billToReceiveInstallmentDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToReceiveInstallmentDTO;
	}
}
