package br.com.api.flow.paymentmethod.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.converter.PaymentMethodMapper;
import br.com.api.dto.PaymentMethodDTO;
import br.com.api.entity.repository.PaymentMethodRepository;

@Component
public class InsertPaymentMethodFlowItem {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private UpdatePaymentMethodFlowItem updatePaymentMethodFlowItem;

	@Autowired
	private PaymentMethodMapper paymentMethodMapper;

	@Transactional
	public PaymentMethodDTO insert(@NonNull PaymentMethodDTO paymentMethod) {

		if (Objects.nonNull(paymentMethod.getIdentifier())) {
			return updatePaymentMethodFlowItem.update(paymentMethod);
		}

		return paymentMethodMapper.toDTO(paymentMethodRepository.save(paymentMethodMapper.toEntity(paymentMethod)));
	}
}
