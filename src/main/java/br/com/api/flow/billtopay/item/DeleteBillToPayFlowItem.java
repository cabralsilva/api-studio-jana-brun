package br.com.api.flow.billtopay.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToPayRepository;

@Component
public class DeleteBillToPayFlowItem {

	@Autowired
	private BillToPayRepository billToPayRepository;

	public void delete(Integer identifier) {

		billToPayRepository.deleteById(identifier);
	}
}
