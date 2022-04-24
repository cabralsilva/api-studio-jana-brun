package br.com.api.flow.billtoreceive.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToReceiveRepository;

@Component
public class SetPaymentConditionToReceiveFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	public void update(Integer identifier, Integer paymentCondition) {

		
		billToReceiveRepository.updatePaymentConditionByIdentifier(paymentCondition, identifier);
	}
}
