package br.com.api.flow.paymentcondition.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.PaymentConditionRepository;

@Component
public class DeletePaymentConditionFlowItem {

	@Autowired
	private PaymentConditionRepository paymentConditionRepository;

	public void delete(Integer identifier) {

		paymentConditionRepository.deleteById(identifier);
	}
}
