package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_MATRICULATION_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentPaymentMapper;
import br.com.api.entity.BillToReceiveInstallmentPayment;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepository;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Component
public class SearchBillToReceiveInstallmentPaymentByFilterFlowItem {

	@Autowired
	private BillToReceiveInstallmentPaymentRepositoryImpl billToReceiveInstallmentPaymentRepositoryImpl;
	@Autowired
	private BillToReceiveInstallmentPaymentRepository billToReceiveInstallmentPaymentRepository;

	@Autowired
	private BillToReceiveInstallmentPaymentMapper billToReceiveInstallmentPaymentMapper;

	public BillToReceiveInstallmentPaymentFilter search(BillToReceiveInstallmentPaymentFilter filter)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		if (Boolean.TRUE.equals(filter.getCustomSearch())) {
			return searchCustom(filter);
		}
		Example<BillToReceiveInstallmentPayment> example = Example
				.of(billToReceiveInstallmentPaymentMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = billToReceiveInstallmentPaymentRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(
					ret.stream().map(p -> billToReceiveInstallmentPaymentMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = billToReceiveInstallmentPaymentRepository.findAll(example);
		filter.setResult(
				ret.stream().map(p -> billToReceiveInstallmentPaymentMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	private BillToReceiveInstallmentPaymentFilter searchCustom(BillToReceiveInstallmentPaymentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToReceiveInstallmentPayment> entities = billToReceiveInstallmentPaymentRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToReceiveInstallmentPaymentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToReceiveInstallmentPayment> entities = billToReceiveInstallmentPaymentRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToReceiveInstallmentPaymentMapper.toDTO(entity))
					.collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_MATRICULATION_FOUND);
		}

		return filter;
	}
}
