package br.com.api.flow.paymentcondition.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckPaymentConditionFlowItem {

	@Autowired
	private FindPaymentConditionByFilterFlowItem findPaymentConditionByFilterFlowItem;

	public PaymentConditionDTO checkIfExist(PaymentConditionDTO paymentConditionDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new PaymentConditionFilter();
		filter.setExample(paymentConditionDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findPaymentConditionByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			paymentConditionDTO.setIdentifier(existing.get().getIdentifier());
		}
		return paymentConditionDTO;
	}
}
