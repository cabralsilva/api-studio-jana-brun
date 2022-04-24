package br.com.api.flow.billtoreceive.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveMapper;
import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveRepository;

@Component
public class UpdateBillToReceiveFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	@Autowired
	private BillToReceiveMapper billToReceiveMapper;

	public BillToReceiveDTO update(BillToReceiveDTO billToReceive) {

		return billToReceiveMapper.toDTO(billToReceiveRepository.save(billToReceiveMapper.toEntity(billToReceive)));
	}
}
