package br.com.api.flow.paymentcondition.item;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.enums.StatusActiveEnum;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Service
public class GetPaymentConditionFlowItem {

	@Autowired
	private FindPaymentConditionByFilterFlowItem findPaymentConditionByFilterFlowItem;

	public Optional<PaymentConditionDTO> getByIdentifierAndActive(Integer identifier, Boolean active)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		final var filter = PaymentConditionFilter.builder()
				.example(PaymentConditionDTO.builder().identifier(identifier).status(StatusActiveEnum.ACTIVE).build())
				.resultUnique(Boolean.TRUE).build();

		return findPaymentConditionByFilterFlowItem.findByFilter((PaymentConditionFilter) filter).getResult().stream()
				.findFirst();
	}
}
