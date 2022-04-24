package br.com.api.flow.billtopayinstallmentpayment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentPaymentMapper;
import br.com.api.entity.BillToPayInstallmentPayment;
import br.com.api.entity.repository.BillToPayInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToPayInstallmentPaymentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToPayInstallmentPaymentByFilterFlowItem {

	@Autowired
	private BillToPayInstallmentPaymentRepositoryImpl billToPayInstallmentPaymentRepositoryImpl;

	@Autowired
	private BillToPayInstallmentPaymentMapper billToPayInstallmentPaymentMapper;

	public BillToPayInstallmentPaymentFilter findByFilter(BillToPayInstallmentPaymentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToPayInstallmentPayment> entities = billToPayInstallmentPaymentRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToPayInstallmentPaymentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToPayInstallmentPayment> entities = billToPayInstallmentPaymentRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToPayInstallmentPaymentMapper.toDTO(entity))
					.collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
