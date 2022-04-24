package br.com.api.flow.billtopayinstallment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToPayInstallmentRepository;

@Component
public class DeleteBillToPayInstallmentFlowItem {

	@Autowired
	private BillToPayInstallmentRepository billToPayInstallmentRepository;

	public void delete(Integer identifier) {

		billToPayInstallmentRepository.deleteById(identifier);
	}
}
