package br.com.api.flow.billtopayinstallmentpayment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToPayInstallmentPaymentRepository;

@Component
public class DeleteBillToPayInstallmentPaymentFlowItem {

	@Autowired
	private BillToPayInstallmentPaymentRepository billToPayInstallmentPaymentRepository;

	public void delete(Integer identifier) {

		billToPayInstallmentPaymentRepository.deleteById(identifier);
	}
}
