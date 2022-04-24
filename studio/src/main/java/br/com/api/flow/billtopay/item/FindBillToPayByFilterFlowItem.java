package br.com.api.flow.billtopay.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayMapper;
import br.com.api.entity.BillToPay;
import br.com.api.entity.repository.BillToPayFilter;
import br.com.api.entity.repository.BillToPayRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToPayByFilterFlowItem {

	@Autowired
	private BillToPayRepositoryImpl billToPayRepositoryImpl;

	@Autowired
	private BillToPayMapper billToPayMapper;

	public BillToPayFilter findByFilter(BillToPayFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToPay> entities = billToPayRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToPayMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToPay> entities = billToPayRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToPayMapper.toDTO(entity)).collect(Collectors.toList()));			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
