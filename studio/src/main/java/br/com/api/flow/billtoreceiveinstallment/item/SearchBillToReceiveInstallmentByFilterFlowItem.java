package br.com.api.flow.billtoreceiveinstallment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_MATRICULATION_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentMapper;
import br.com.api.entity.BillToReceiveInstallment;
import br.com.api.entity.repository.BillToReceiveInstallmentFilter;
import br.com.api.entity.repository.BillToReceiveInstallmentRepository;
import br.com.api.entity.repository.BillToReceiveInstallmentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;
import lombok.var;

@Component
public class SearchBillToReceiveInstallmentByFilterFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepository billToReceiveInstallmentRepository;
	@Autowired
	private BillToReceiveInstallmentRepositoryImpl billToReceiveInstallmentRepositoryImpl;

	@Autowired
	private BillToReceiveInstallmentMapper billToReceiveInstallmentMapper;

	public BillToReceiveInstallmentFilter search(BillToReceiveInstallmentFilter filter)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		if (Boolean.TRUE.equals(filter.getCustomSearch())) {
			return searchCustom(filter);
		}
		Example<BillToReceiveInstallment> example = Example
				.of(billToReceiveInstallmentMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = billToReceiveInstallmentRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(
					ret.stream().map(p -> billToReceiveInstallmentMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = billToReceiveInstallmentRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> billToReceiveInstallmentMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}

	private BillToReceiveInstallmentFilter searchCustom(BillToReceiveInstallmentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToReceiveInstallment> entities = billToReceiveInstallmentRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToReceiveInstallmentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToReceiveInstallment> entities = billToReceiveInstallmentRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToReceiveInstallmentMapper.toDTO(entity))
					.collect(Collectors.toList()));
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_MATRICULATION_FOUND);
		}

		return filter;
	}
}
