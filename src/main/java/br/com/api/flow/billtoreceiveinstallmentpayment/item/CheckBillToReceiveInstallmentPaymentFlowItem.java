package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckBillToReceiveInstallmentPaymentFlowItem {

	@Autowired
	private FindBillToReceiveInstallmentPaymentByFilterFlowItem findBillToReceiveInstallmentPaymentByFilterFlowItem;

	public BillToReceiveInstallmentPaymentDTO checkIfExist(
			BillToReceiveInstallmentPaymentDTO billToReceiveInstallmentPaymentDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new BillToReceiveInstallmentPaymentFilter();
		filter.setExample(billToReceiveInstallmentPaymentDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findBillToReceiveInstallmentPaymentByFilterFlowItem.findByFilter(filter).getResult()
				.stream().findFirst();

		if (existing.isPresent()) {
			billToReceiveInstallmentPaymentDTO.setIdentifier(existing.get().getIdentifier());
		}
		return billToReceiveInstallmentPaymentDTO;
	}
}
