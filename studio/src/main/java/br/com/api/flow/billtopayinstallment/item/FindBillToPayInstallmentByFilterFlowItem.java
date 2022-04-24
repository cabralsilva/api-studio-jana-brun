package br.com.api.flow.billtopayinstallment.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentMapper;
import br.com.api.entity.BillToPayInstallment;
import br.com.api.entity.repository.BillToPayInstallmentFilter;
import br.com.api.entity.repository.BillToPayInstallmentRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToPayInstallmentByFilterFlowItem {

	@Autowired
	private BillToPayInstallmentRepositoryImpl billToPayInstallmentRepositoryImpl;

	@Autowired
	private BillToPayInstallmentMapper billToPayInstallmentMapper;

	public BillToPayInstallmentFilter findByFilter(BillToPayInstallmentFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToPayInstallment> entities = billToPayInstallmentRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToPayInstallmentMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToPayInstallment> entities = billToPayInstallmentRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToPayInstallmentMapper.toDTO(entity)).collect(Collectors.toList()));			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
