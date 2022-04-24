package br.com.api.flow.billtopay.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToPayRepository;

@Component
public class SetPaymentConditionToPayFlowItem {

	@Autowired
	private BillToPayRepository billToPayRepository;

	public void update(Integer identifier, Integer paymentCondition) {

		
		billToPayRepository.updatePaymentConditionByIdentifier(paymentCondition, identifier);
	}
}
