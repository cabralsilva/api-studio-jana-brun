package br.com.api.flow.billtopayinstallmentpayment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentPaymentMapper;
import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.entity.repository.BillToPayInstallmentPaymentRepository;

@Component
public class UpdateBillToPayInstallmentPaymentFlowItem {

	@Autowired
	private BillToPayInstallmentPaymentRepository billToPayInstallmentPaymentRepository;

	@Autowired
	private BillToPayInstallmentPaymentMapper billToPayInstallmentPaymentMapper;

	public BillToPayInstallmentPaymentDTO update(BillToPayInstallmentPaymentDTO billToPayInstallmentPayment) {

		return billToPayInstallmentPaymentMapper.toDTO(billToPayInstallmentPaymentRepository.save(billToPayInstallmentPaymentMapper.toEntity(billToPayInstallmentPayment)));
	}
}
