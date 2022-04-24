package br.com.api.flow.billtoreceive.item;

import static br.com.api.exceptions.FindByFilterExceptionEnum.MORE_THAN_ONE_matriculation_FOUND;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveMapper;
import br.com.api.entity.BillToReceive;
import br.com.api.entity.repository.BillToReceiveFilter;
import br.com.api.entity.repository.BillToReceiveRepositoryImpl;
import br.com.api.exceptions.FindByFilterException;

@Component
public class FindBillToReceiveByFilterFlowItem {

	@Autowired
	private BillToReceiveRepositoryImpl billToReceiveRepositoryImpl;

	@Autowired
	private BillToReceiveMapper billToReceiveMapper;

	public BillToReceiveFilter findByFilter(BillToReceiveFilter filter)
			throws FindByFilterException, NoSuchMethodException, SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, InstantiationException {

		if (Boolean.TRUE.equals(filter.getPageable())) {
			Page<BillToReceive> entities = billToReceiveRepositoryImpl.findByFilter(filter,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));

			filter.setResult(entities.map(entity -> billToReceiveMapper.toDTO(entity)).toList());
			filter.setTotal(Math.toIntExact(entities.getTotalElements()));
			filter.setTotalPages(Math.toIntExact(entities.getTotalPages()));
			filter.setLast(entities.isLast());
		} else {
			List<BillToReceive> entities = billToReceiveRepositoryImpl.findByFilter(filter);
			filter.setResult(entities.stream().map(entity -> billToReceiveMapper.toDTO(entity)).collect(Collectors.toList()));			
		}

		if (Boolean.TRUE.equals(filter.getResultUnique()) && filter.getResult().size() > 1) {
			throw new FindByFilterException(MORE_THAN_ONE_matriculation_FOUND);
		}

		return filter;
	}
}
