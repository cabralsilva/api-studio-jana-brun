package br.com.api.flow.billtoreceiveinstallment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToReceiveInstallmentRepository;

@Component
public class DeleteBillToReceiveInstallmentFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepository billToReceiveInstallmentRepository;

	public void delete(Integer identifier) {

		billToReceiveInstallmentRepository.deleteById(identifier);
	}
}
