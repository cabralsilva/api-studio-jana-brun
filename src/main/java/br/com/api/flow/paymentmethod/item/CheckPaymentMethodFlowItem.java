package br.com.api.flow.paymentmethod.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentMethodDTO;
import br.com.api.entity.repository.PaymentMethodFilter;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class CheckPaymentMethodFlowItem {

	@Autowired
	private FindPaymentMethodByFilterFlowItem findPaymentMethodByFilterFlowItem;

	public PaymentMethodDTO checkIfExist(PaymentMethodDTO paymentMethodDTO)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = new PaymentMethodFilter();
		filter.setExample(paymentMethodDTO);
		filter.setPageable(Boolean.FALSE);

		final var existing = findPaymentMethodByFilterFlowItem.findByFilter(filter).getResult().stream().findFirst();

		if (existing.isPresent()) {
			paymentMethodDTO.setIdentifier(existing.get().getIdentifier());
		}
		return paymentMethodDTO;
	}
}
