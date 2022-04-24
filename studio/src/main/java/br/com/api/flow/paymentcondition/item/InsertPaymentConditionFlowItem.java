package br.com.api.flow.paymentcondition.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.converter.PaymentConditionMapper;
import br.com.api.dto.PaymentConditionDTO;
import br.com.api.entity.repository.PaymentConditionRepository;

@Component
public class InsertPaymentConditionFlowItem {

	@Autowired
	private PaymentConditionRepository paymentConditionRepository;

	@Autowired
	private UpdatePaymentConditionFlowItem updatePaymentConditionFlowItem;

	@Autowired
	private PaymentConditionMapper paymentConditionMapper;

	@Transactional
	public PaymentConditionDTO insert(@NonNull PaymentConditionDTO paymentCondition) {

		if (Objects.nonNull(paymentCondition.getIdentifier())) {
			return updatePaymentConditionFlowItem.update(paymentCondition);
		}

		return paymentConditionMapper.toDTO(paymentConditionRepository.save(paymentConditionMapper.toEntity(paymentCondition)));
	}
}
