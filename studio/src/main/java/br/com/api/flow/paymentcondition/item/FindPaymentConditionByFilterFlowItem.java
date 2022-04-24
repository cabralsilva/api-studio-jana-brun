package br.com.api.flow.paymentcondition.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PaymentConditionMapper;
import br.com.api.entity.PaymentCondition;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.entity.repository.PaymentConditionRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindPaymentConditionByFilterFlowItem {

	@Autowired
	private PaymentConditionRepositoryImpl paymentConditionRepositoryImpl;

	@Autowired
	private PaymentConditionMapper paymentConditionMapper;

	public PaymentConditionFilter findByFilter(PaymentConditionFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<PaymentCondition> entities = paymentConditionRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> paymentConditionMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<PaymentCondition> entities = paymentConditionRepositoryImpl.findByFilter(filter);
			filter.setResult(
					entities.stream().map(entity -> paymentConditionMapper.toDTO(entity)).collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
