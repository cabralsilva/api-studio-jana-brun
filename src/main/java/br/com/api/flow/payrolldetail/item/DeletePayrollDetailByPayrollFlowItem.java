package br.com.api.flow.payrolldetail.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.PayrollDetailRepository;

@Component
public class DeletePayrollDetailByPayrollFlowItem {

	@Autowired
	private PayrollDetailRepository payrollDetailRepository;

	public void delete(Integer payrollIdentifier) {

		payrollDetailRepository.deleteByPayrollIdentifier(payrollIdentifier);
	}
}
