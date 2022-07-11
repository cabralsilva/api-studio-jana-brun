package br.com.api.flow.paymentcondition.item;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.exceptions.FindByFilterExceptionEnum;
import lombok.var;

@Service
public class CheckAndGetPaymentConditionFlowItem {

	@Autowired
	private GetPaymentConditionFlowItem getPaymentConditionFlowItem;

	public PaymentConditionDTO checkAndGet(Integer identifier)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var existing = getPaymentConditionFlowItem.getByIdentifierAndActive(identifier, Boolean.TRUE);

		if (existing.isPresent())
			return existing.get();

		throw new FindByFilterException(FindByFilterExceptionEnum.RESOURCE_NOT_FOUND);
	}
}
