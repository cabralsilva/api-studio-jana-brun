package br.com.api.flow.payroll.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.PayrollRepository;
import br.com.api.enums.PayrollStatusEnum;

@Component
public class UpdateStatusPayrollFlowItem {

	@Autowired
	private PayrollRepository payrollRepository;

	public void update(Integer identifier, PayrollStatusEnum status) {

		payrollRepository.update(status, identifier);
	}
}
