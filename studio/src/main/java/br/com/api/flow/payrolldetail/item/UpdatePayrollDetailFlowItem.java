package br.com.api.flow.payrolldetail.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PayrollDetailMapper;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PayrollDetailRepository;

@Component
public class UpdatePayrollDetailFlowItem {

	@Autowired
	private PayrollDetailRepository payrollDetailRepository;

	@Autowired
	private PayrollDetailMapper payrollDetailMapper;

	public PayrollDetailDTO update(PayrollDetailDTO payroll) {

		return payrollDetailMapper.toDTO(payrollDetailRepository.save(payrollDetailMapper.toEntity(payroll)));
	}
}
