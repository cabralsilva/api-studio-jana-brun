package br.com.api.flow.billtopayinstallmentpayment.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentPaymentMapper;
import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToPayInstallmentPaymentRepository;

@Component
public class InsertBillToPayInstallmentPaymentFlowItem {

	@Autowired
	private BillToPayInstallmentPaymentRepository billToPayInstallmentPaymentRepository;

	@Autowired
	private UpdateBillToPayInstallmentPaymentFlowItem updateBillToPayInstallmentPaymentFlowItem;

	@Autowired
	private BillToPayInstallmentPaymentMapper billToPayInstallmentPaymentMapper;

	public BillToPayInstallmentPaymentDTO insert(@NonNull BillToPayInstallmentPaymentDTO billToPayInstallmentPayment) {

		if (Objects.nonNull(billToPayInstallmentPayment.getIdentifier())) {
			return updateBillToPayInstallmentPaymentFlowItem.update(billToPayInstallmentPayment);
		}

		return billToPayInstallmentPaymentMapper.toDTO(billToPayInstallmentPaymentRepository.save(billToPayInstallmentPaymentMapper.toEntity(billToPayInstallmentPayment)));
	}
}
