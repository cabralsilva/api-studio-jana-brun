package br.com.api.flow.billtoreceiveinstallment.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentMapper;
import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentRepository;

@Component
public class InsertBillToReceiveInstallmentFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepository billToReceiveInstallmentRepository;

	@Autowired
	private UpdateBillToReceiveInstallmentFlowItem updateBillToReceiveInstallmentFlowItem;

	@Autowired
	private BillToReceiveInstallmentMapper billToReceiveInstallmentMapper;

	public BillToReceiveInstallmentDTO insert(@NonNull BillToReceiveInstallmentDTO billToReceiveInstallment) {

		if (Objects.nonNull(billToReceiveInstallment.getIdentifier())) {
			return updateBillToReceiveInstallmentFlowItem.update(billToReceiveInstallment);
		}

		return billToReceiveInstallmentMapper.toDTO(billToReceiveInstallmentRepository.save(billToReceiveInstallmentMapper.toEntity(billToReceiveInstallment)));
	}
}
