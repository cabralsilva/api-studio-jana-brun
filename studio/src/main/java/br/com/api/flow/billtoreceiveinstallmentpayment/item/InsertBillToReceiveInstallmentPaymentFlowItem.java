package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentPaymentMapper;
import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepository;

@Component
public class InsertBillToReceiveInstallmentPaymentFlowItem {

	@Autowired
	private BillToReceiveInstallmentPaymentRepository billToReceiveInstallmentPaymentRepository;

	@Autowired
	private UpdateBillToReceiveInstallmentPaymentFlowItem updateBillToReceiveInstallmentPaymentFlowItem;

	@Autowired
	private BillToReceiveInstallmentPaymentMapper billToReceiveInstallmentPaymentMapper;

	public BillToReceiveInstallmentPaymentDTO insert(@NonNull BillToReceiveInstallmentPaymentDTO billToReceiveInstallmentPayment) {

		if (Objects.nonNull(billToReceiveInstallmentPayment.getIdentifier())) {
			return updateBillToReceiveInstallmentPaymentFlowItem.update(billToReceiveInstallmentPayment);
		}

		return billToReceiveInstallmentPaymentMapper.toDTO(billToReceiveInstallmentPaymentRepository.save(billToReceiveInstallmentPaymentMapper.toEntity(billToReceiveInstallmentPayment)));
	}
}
