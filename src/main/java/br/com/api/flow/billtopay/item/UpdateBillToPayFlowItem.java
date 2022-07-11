package br.com.api.flow.billtopay.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayMapper;
import br.com.api.dto.BillToPayDTO;
import br.com.api.entity.repository.BillToPayRepository;

@Component
public class UpdateBillToPayFlowItem {

	@Autowired
	private BillToPayRepository billToPayRepository;

	@Autowired
	private BillToPayMapper billToPayMapper;

	public BillToPayDTO update(BillToPayDTO billToPay) {

		return billToPayMapper.toDTO(billToPayRepository.save(billToPayMapper.toEntity(billToPay)));
	}
}
