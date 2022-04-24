package br.com.api.flow.billtoreceiveinstallment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentMapper;
import br.com.api.entity.BillToReceiveInstallment;
import br.com.api.entity.repository.BillToReceiveInstallmentFilter;
import br.com.api.entity.repository.BillToReceiveInstallmentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToReceiveInstallmentByFilterFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepositoryImpl billToReceiveInstallmentRepositoryImpl;

	@Autowired
	private BillToReceiveInstallmentMapper billToReceiveInstallmentMapper;

	public BillToReceiveInstallmentFilter findByFilter(BillToReceiveInstallmentFilter filter)
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
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
