package br.com.api.flow.billtopayinstallmentpayment.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToPayInstallmentPaymentFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToPayInstallmentPaymentFlowItem {

	@Autowired
	private FindBillToPayInstallmentPaymentByFilterFlowItem findBillToPayInstallmentPaymentByFilterFlowItem;

	public BillToPayInstallmentPaymentDTO checkIfExist(BillToPayInstallmentPaymentDTO billToPayInstallmentPaymentDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToPayInstallmentPaymentFilter();
		filter.setExample(billToPayInstallmentPaymentDTO);
		filter.setPageable(Boolean.FALSE);
		
		final var existing = findBillToPayInstallmentPaymentByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			billToPayInstallmentPaymentDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToPayInstallmentPaymentDTO;
	}
}
