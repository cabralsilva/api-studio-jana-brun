package br.com.api.flow.payroll.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.PayrollMapper;
import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PayrollRepository;
import br.com.api.enums.PayrollStatusEnum;
import br.com.api.flow.payrolldetail.item.DeletePayrollDetailByPayrollFlowItem;
import br.com.api.flow.payrolldetail.item.InsertPayrollDetailFlowItem;

@Component
public class InsertPayrollFlowItem {

	@Autowired
	private PayrollRepository payrollRepository;

	@Autowired
	private UpdatePayrollFlowItem updatePayrollFlowItem;

	@Autowired
	private InsertPayrollDetailFlowItem insertPayrollDetailFlowItem;

	@Autowired
	private PayrollMapper payrollMapper;

	public PayrollDTO insert(@NonNull PayrollDTO payrollDTO) {

		payrollDTO.setStatus(PayrollStatusEnum.AWAITING_APPROVE);
		if (Objects.nonNull(payrollDTO.getIdentifier())) {
			return updatePayrollFlowItem.update(payrollDTO);
		}

		final PayrollDTO payrollDTOAux = payrollMapper
				.toDTO(payrollRepository.save(payrollMapper.toEntity(payrollDTO)));

		for (PayrollDetailDTO payrollItem : payrollDTO.getPayrollDetailList()) {
			payrollItem.setPayroll(PayrollDTO.builder().identifier(payrollDTOAux.getIdentifier()).build());
			payrollItem = insertPayrollDetailFlowItem.insert(payrollItem);
		}

		return payrollDTOAux;
	}
}
