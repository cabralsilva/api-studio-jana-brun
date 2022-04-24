package br.com.api.flow.billtoreceive.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveMapper;
import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveRepository;

@Component
public class InsertBillToReceiveFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	@Autowired
	private UpdateBillToReceiveFlowItem updateBillToReceiveFlowItem;

	@Autowired
	private BillToReceiveMapper billToReceiveMapper;

	public BillToReceiveDTO insert(@NonNull BillToReceiveDTO billToReceive) {

		if (Objects.nonNull(billToReceive.getIdentifier())) {
			return updateBillToReceiveFlowItem.update(billToReceive);
		}

		return billToReceiveMapper.toDTO(billToReceiveRepository.save(billToReceiveMapper.toEntity(billToReceive)));
	}
}
