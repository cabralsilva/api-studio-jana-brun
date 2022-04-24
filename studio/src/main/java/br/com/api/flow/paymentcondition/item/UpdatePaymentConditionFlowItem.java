package br.com.api.flow.paymentcondition.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PaymentConditionMapper;
import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.repository.PaymentConditionRepository;

@Component
public class UpdatePaymentConditionFlowItem {

	@Autowired
	private PaymentConditionRepository paymentConditionRepository;

	@Autowired
	private PaymentConditionMapper paymentConditionMapper;

	public PaymentConditionDTO update(PaymentConditionDTO paymentCondition) {

		return paymentConditionMapper.toDTO(paymentConditionRepository.save(paymentConditionMapper.toEntity(paymentCondition)));
	}
}
