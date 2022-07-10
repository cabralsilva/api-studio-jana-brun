package br.com.api.flow.payroll.item;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.api.converter.PayrollMapper;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.Payroll;
import br.com.api.entity.repository.PayrollFilter;
import br.com.api.entity.repository.PayrollRepository;
import lombok.var;

@Component
public class SearchPayrollByFilterFlowItem {

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private PayrollMapper payrollMapper;

	public PayrollFilter search(PayrollFilter filter) {

		Example<Payroll> example = Example.of(payrollMapper.toEntity(filter.getExample()));
		if (Boolean.TRUE.equals(filter.getPageable())) {
			final var ret = payrollRepository.findAll(example,
					PageRequest.of(filter.getCurrentPage(), filter.getSizePage()));
			filter.setResult(ret.stream().map(p -> payrollMapper.toDTO(p)).collect(Collectors.toList()));
			filter.setTotal((int) ret.getTotalElements());
			filter.setTotalPages(ret.getTotalPages());
			return filter;
		}
		final var ret = payrollRepository.findAll(example);
		filter.setResult(ret.stream().map(p -> payrollMapper.toDTO(p)).collect(Collectors.toList()));
		return filter;
	}
}
