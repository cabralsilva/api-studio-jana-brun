package br.com.api.flow.payroll.item;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToPayDTO;
import br.com.api.dto.EmployeeDTO;
import br.com.api.dto.PaymentConditionDTO;
import br.com.api.dto.PayrollDTO;
import br.com.api.dto.PayrollDetailDTO;
import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.enums.PayrollStatusEnum;
import br.com.api.enums.StatusOfBillEnum;
import br.com.api.flow.billtopay.item.InsertBillToPayFlowItem;
import br.com.api.flow.paymentcondition.item.FindPaymentConditionByFilterFlowItem;

@Service
public class CheckoutPayrollFlowItem {

	@Autowired
	private InsertBillToPayFlowItem insertBillToPayFlowItem;
	@Autowired
	private FindPaymentConditionByFilterFlowItem searchPaymentConditionByFilterFlowItem;
	@Autowired
	private UpdateStatusPayrollFlowItem updateStatusPayrollFlowItem;

	@Transactional
	public void checkout(PayrollDTO payrollDTO) {

		Map<EmployeeDTO, List<PayrollDetailDTO>> mapEmployeePayroll = payrollDTO.getPayrollDetailList().stream()
				.collect(Collectors.groupingBy(PayrollDetailDTO::getEmployee));

		mapEmployeePayroll.entrySet().forEach(employeePayroll -> {

			BillToPayDTO billToPay = BillToPayDTO.builder().emissionDate(payrollDTO.getTargetDate())
					.person(employeePayroll.getKey().getPerson())
					.paymentCondition(searchPaymentConditionByFilterFlowItem
							.findByFilter(PaymentConditionFilter.builder()
									.example(PaymentConditionDTO.builder().identifier(1).build()).build())
							.getResult().get(0))
					.observation(StringUtils.join(
							employeePayroll.getValue().stream().map(PayrollDetailDTO::getDescription).toArray(), " | "))
					.status(StatusOfBillEnum.OPENED)
					.title(payrollDTO.getIdentifier() + "_" + employeePayroll.getKey().getIdentifier() + "_"
							+ payrollDTO.getInitDate().format(DateTimeFormatter.ofPattern("MM/yyyy")))
					.value(employeePayroll.getValue().stream().mapToDouble(PayrollDetailDTO::getValue).sum()).build();

			if (billToPay.getValue() > 0)
				insertBillToPayFlowItem.insert(billToPay);
		});

		updateStatusPayrollFlowItem.update(payrollDTO.getIdentifier(), PayrollStatusEnum.CONFIRMED);
	}
}
