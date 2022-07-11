package br.com.api.flow.billtoreceiveinstallmentpayment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToReceiveInstallmentPaymentRepository;

@Component
public class DeleteBillToReceiveInstallmentPaymentFlowItem {

	@Autowired
	private BillToReceiveInstallmentPaymentRepository billToReceiveInstallmentPaymentRepository;

	public void delete(Integer identifier) {

		billToReceiveInstallmentPaymentRepository.deleteById(identifier);
	}
}
