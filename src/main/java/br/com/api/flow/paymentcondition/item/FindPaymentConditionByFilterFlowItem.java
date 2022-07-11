package br.com.api.flow.paymentcondition.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PaymentConditionMapper;
import br.com.api.entity.PaymentCondition;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.entity.repository.PaymentConditionRepository;
import lombok.var;

@Component
public class FindPaymentConditionByFilterFlowItem {

	@Autowired
	private PaymentConditionRepository paymentConditionRepository;

	@Autowired
	private PaymentConditionMapper paymentConditionMapper;

	public PaymentConditionFilter findByFilter(PaymentConditionFilter filter) {

		Example<PaymentCondition> example = Example.of(paymentConditionMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = paymentConditionRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> paymentConditionMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = paymentConditionRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> paymentConditionMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
