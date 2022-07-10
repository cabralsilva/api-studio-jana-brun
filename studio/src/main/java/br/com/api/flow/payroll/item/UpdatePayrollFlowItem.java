package br.com.api.flow.payroll.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.PayrollMapper;
import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PayrollRepository;
import br.com.api.flow.payrolldetail.item.DeletePayrollDetailByPayrollFlowItem;
import br.com.api.flow.payrolldetail.item.InsertPayrollDetailFlowItem;

@Component
public class UpdatePayrollFlowItem {

	@Autowired
	private PayrollRepository payrollRepository;
	
	@Autowired
	private InsertPayrollDetailFlowItem insertPayrollDetailFlowItem;

	@Autowired
	private DeletePayrollDetailByPayrollFlowItem deletePayrollDetailByPayrollFlowItem;

	@Autowired
	private PayrollMapper payrollMapper;

	public PayrollDTO update(PayrollDTO payrollDTO) {

		final PayrollDTO payrollDTOAux = payrollMapper.toDTO(payrollRepository.save(payrollMapper.toEntity(payrollDTO)));
		

		deletePayrollDetailByPayrollFlowItem.delete(payrollDTOAux.getIdentifier());
		for (PayrollDetailDTO payrollItem : payrollDTO.getPayrollDetailList()) {
			payrollItem.setPayroll(PayrollDTO.builder().identifier(payrollDTOAux.getIdentifier()).build());
			payrollItem = insertPayrollDetailFlowItem.insert(payrollItem);
		}
		
		return payrollDTOAux;
	}
}
