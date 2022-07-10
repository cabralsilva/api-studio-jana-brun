package br.com.api.flow.billtoreceiveinstallment.item;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentFilter;
import br.com.api.entity.repository.BillToReceiveInstallmentRepository;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.enums.PaymentStatusEnum;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.billtoreceiveinstallmentpayment.item.SearchBillToReceiveInstallmentPaymentByFilterFlowItem;

@Component
public class UpdateStatusBillToReceiveInstallmentFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepository billToReceiveInstallmentRepository;

	@Autowired
	private SearchBillToReceiveInstallmentPaymentByFilterFlowItem searchBillToReceiveInstallmentPaymentByFilterFlowItem;

	public void update(BillToReceiveInstallmentDTO billToReceiveInstallment)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		List<BillToReceiveInstallmentPaymentDTO> paymentList = searchBillToReceiveInstallmentPaymentByFilterFlowItem
				.search(BillToReceiveInstallmentPaymentFilter.builder()
						.example(BillToReceiveInstallmentPaymentDTO.builder()
								.installment(BillToReceiveInstallmentDTO.builder()
										.identifier(billToReceiveInstallment.getIdentifier())
										.build())
								.build())
						.build()).getResult();

		Double totalPaid = paymentList.stream().filter(payment -> payment.getStatus() == PaymentStatusEnum.SETTLED)
				.mapToDouble(BillToReceiveInstallmentPaymentDTO::getValue).sum();

		InstallmentStatusEnum statusOfInstallment = InstallmentStatusEnum.OPENED;
		if (Objects.equals(totalPaid, billToReceiveInstallment.getValue())) {
			statusOfInstallment = InstallmentStatusEnum.SETTLED;
		} else if (totalPaid == 0D) {
			statusOfInstallment = InstallmentStatusEnum.OPENED;
		} else if (billToReceiveInstallment.getValue() == 0D) {
			statusOfInstallment = InstallmentStatusEnum.SETTLED;
		} else if (totalPaid > billToReceiveInstallment.getValue()) {
			statusOfInstallment = InstallmentStatusEnum.SETTLED;
		} else {
			statusOfInstallment = InstallmentStatusEnum.PARTIALLY;
		}

		billToReceiveInstallmentRepository.updateStatus(statusOfInstallment, billToReceiveInstallment.getIdentifier());
	}
}
