package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentPaymentMapper;
import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepository;

@Component
public class UpdateBillToReceiveInstallmentPaymentFlowItem {

	@Autowired
	private BillToReceiveInstallmentPaymentRepository billToReceiveInstallmentPaymentRepository;

	@Autowired
	private BillToReceiveInstallmentPaymentMapper billToReceiveInstallmentPaymentMapper;

	public BillToReceiveInstallmentPaymentDTO update(BillToReceiveInstallmentPaymentDTO billToReceiveInstallmentPayment) {

		return billToReceiveInstallmentPaymentMapper.toDTO(billToReceiveInstallmentPaymentRepository.save(billToReceiveInstallmentPaymentMapper.toEntity(billToReceiveInstallmentPayment)));
	}
}
