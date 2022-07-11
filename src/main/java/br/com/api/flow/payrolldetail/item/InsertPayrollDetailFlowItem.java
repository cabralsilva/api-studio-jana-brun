package br.com.api.flow.payrolldetail.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.PayrollDetailMapper;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PayrollDetailRepository;

@Component
public class InsertPayrollDetailFlowItem {

	@Autowired
	private PayrollDetailRepository payrollDetailRepository;

	@Autowired
	private PayrollDetailMapper payrollDetailMapper;

	@Autowired
	private UpdatePayrollDetailFlowItem updatePayrollDetailFlowItem;

	public PayrollDetailDTO insert(@NonNull PayrollDetailDTO payrollDetailDTO) {

		if (Objects.nonNull(payrollDetailDTO.getIdentifier())) {
			return updatePayrollDetailFlowItem.update(payrollDetailDTO);
		}

		return payrollDetailMapper
				.toDTO(payrollDetailRepository.saveAndFlush(payrollDetailMapper.toEntity(payrollDetailDTO)));
	}
}
