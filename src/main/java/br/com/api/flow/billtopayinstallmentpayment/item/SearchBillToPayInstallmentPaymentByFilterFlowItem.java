package br.com.api.flow.billtopayinstallmentpayment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_MATRICULATION_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentPaymentMapper;
import br.com.api.entity.BillToPayInstallmentPayment;
import br.com.api.entity.repository.BillToPayInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToPayInstallmentPaymentRepository;
import br.com.api.entity.repository.BillToPayInstallmentPaymentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Component
public class SearchBillToPayInstallmentPaymentByFilterFlowItem {

	@Autowired
	private BillToPayInstallmentPaymentRepositoryImpl billToPayInstallmentPaymentRepositoryImpl;
	@Autowired
	private BillToPayInstallmentPaymentRepository billToPayInstallmentPaymentRepository;

	@Autowired
	private BillToPayInstallmentPaymentMapper billToPayInstallmentPaymentMapper;

	public BillToPayInstallmentPaymentFilter search(BillToPayInstallmentPaymentFilter filter)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		if (Boolean.TRUE.equals(filter.getCustomSearch())) {
			return searchCustom(filter);
		}
		Example<BillToPayInstallmentPayment> example = Example
				.of(billToPayInstallmentPaymentMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = billToPayInstallmentPaymentRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(
					ret.stream().map(p -> billToPayInstallmentPaymentMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = billToPayInstallmentPaymentRepository.findAll(example);
		filter.setResult(
				ret.stream().map(p -> billToPayInstallmentPaymentMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	private BillToPayInstallmentPaymentFilter searchCustom(BillToPayInstallmentPaymentFilter filter)
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
			throw new FindByFilterException(MORE_THAN_ONE_MATRICULATION_FOUND);
		}

		return filter;
	}
}
