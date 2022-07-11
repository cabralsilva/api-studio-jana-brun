package br.com.api.flow.billtopayinstallment.item;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToPayInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToPayInstallmentRepository;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.enums.PaymentStatusEnum;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.billtopayinstallmentpayment.item.SearchBillToPayInstallmentPaymentByFilterFlowItem;

@Component
public class UpdateStatusBillToPayInstallmentFlowItem {

	@Autowired
	private BillToPayInstallmentRepository billToPayInstallmentRepository;

	@Autowired
	private SearchBillToPayInstallmentPaymentByFilterFlowItem searchBillToPayInstallmentPaymentByFilterFlowItem;

	public void update(BillToPayInstallmentDTO billToPayInstallment)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		List<BillToPayInstallmentPaymentDTO> paymentList = searchBillToPayInstallmentPaymentByFilterFlowItem
				.search(BillToPayInstallmentPaymentFilter.builder()
						.example(BillToPayInstallmentPaymentDTO.builder()
								.installment(BillToPayInstallmentDTO.builder()
										.identifier(billToPayInstallment.getIdentifier())
										.build())
								.build())
						.build()).getResult();

		Double totalPaid = paymentList.stream().filter(payment -> payment.getStatus() == PaymentStatusEnum.SETTLED)
				.mapToDouble(BillToPayInstallmentPaymentDTO::getValue).sum();

		InstallmentStatusEnum statusOfInstallment = InstallmentStatusEnum.OPENED;
		if (Objects.equals(totalPaid, billToPayInstallment.getValue())) {
			statusOfInstallment = InstallmentStatusEnum.SETTLED;
		} else if (totalPaid == 0D) {
			statusOfInstallment = InstallmentStatusEnum.OPENED;
		} else if (billToPayInstallment.getValue() == 0D) {
			statusOfInstallment = InstallmentStatusEnum.SETTLED;
		} else {
			statusOfInstallment = InstallmentStatusEnum.PARTIALLY;
		}

		billToPayInstallmentRepository.updateStatus(statusOfInstallment, billToPayInstallment.getIdentifier());
	}
}
