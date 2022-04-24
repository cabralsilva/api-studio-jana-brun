package br.com.api.flow.billtoreceive.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.entity.repository.BillToReceiveRepository;

@Component
public class DeleteBillToReceiveFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	public void delete(Integer identifier) {

		billToReceiveRepository.deleteById(identifier);
	}
}
