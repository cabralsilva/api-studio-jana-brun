package br.com.api.flow.paymentmethod.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PaymentMethodMapper;
import br.com.api.dto.PaymentMethodDTO;
import br.com.api.entity.repository.PaymentMethodRepository;

@Component
public class UpdatePaymentMethodFlowItem {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private PaymentMethodMapper paymentMethodMapper;

	public PaymentMethodDTO update(PaymentMethodDTO paymentMethod) {

		return paymentMethodMapper.toDTO(paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethod)));
	}
}
