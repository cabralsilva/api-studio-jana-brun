package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentPaymentMapper;
import br.com.api.entity.BillToReceiveInstallmentPayment;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToReceiveInstallmentPaymentByFilterFlowItem {

	@Autowired
	private BillToReceiveInstallmentPaymentRepositoryImpl billToReceiveInstallmentPaymentRepositoryImpl;

	@Autowired
	private BillToReceiveInstallmentPaymentMapper billToReceiveInstallmentPaymentMapper;

	public BillToReceiveInstallmentPaymentFilter findByFilter(BillToReceiveInstallmentPaymentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToReceiveInstallmentPayment> entities = billToReceiveInstallmentPaymentRepositoryImpl
					.findByFilter(filter, PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToReceiveInstallmentPaymentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToReceiveInstallmentPayment> entities = billToReceiveInstallmentPaymentRepositoryImpl
					.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToReceiveInstallmentPaymentMapper.toDTO(entity))
					.collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
