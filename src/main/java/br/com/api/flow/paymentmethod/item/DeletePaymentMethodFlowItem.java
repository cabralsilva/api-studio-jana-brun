package br.com.api.flow.paymentmethod.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.PaymentMethodRepository;

@Component
public class DeletePaymentMethodFlowItem {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	public void delete(Integer identifier) {

		paymentMethodRepository.deleteById(identifier);
	}
}
