package br.com.api.flow.supplier.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.SupplierMapper;
import br.com.api.entity.Supplier;
import br.com.api.entity.repository.SupplierFilter;
import br.com.api.entity.repository.SupplierRepository;
import lombok.var;

@Component
public class FindSupplierByFilterFlowItem {
	
	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private SupplierMapper supplierMapper;

	public SupplierFilter findByFilter(SupplierFilter filter) {

		Example<Supplier> example = Example.of(supplierMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = supplierRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> supplierMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = supplierRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> supplierMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
