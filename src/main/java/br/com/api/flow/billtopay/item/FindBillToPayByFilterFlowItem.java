package br.com.api.flow.billtopay.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayMapper;
import br.com.api.entity.BillToPay;
import br.com.api.entity.repository.BillToPayFilter;
import br.com.api.entity.repository.BillToPayRepository;
import lombok.var;

@Component
public class FindBillToPayByFilterFlowItem {

	@Autowired
	private BillToPayRepository billToPayRepository;

	@Autowired
	private BillToPayMapper billToPayMapper;

	public BillToPayFilter findByFilter(BillToPayFilter filter) {

		Example<BillToPay> example = Example.of(billToPayMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = billToPayRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> billToPayMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = billToPayRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> billToPayMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
