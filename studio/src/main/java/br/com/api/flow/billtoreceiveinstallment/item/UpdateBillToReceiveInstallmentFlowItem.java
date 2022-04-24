package br.com.api.flow.billtoreceiveinstallment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveInstallmentMapper;
import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.entity.repository.BillToReceiveInstallmentRepository;

@Component
public class UpdateBillToReceiveInstallmentFlowItem {

	@Autowired
	private BillToReceiveInstallmentRepository billToReceiveInstallmentRepository;

	@Autowired
	private BillToReceiveInstallmentMapper billToReceiveInstallmentMapper;

	public BillToReceiveInstallmentDTO update(BillToReceiveInstallmentDTO billToReceiveInstallment) {

		return billToReceiveInstallmentMapper.toDTO(billToReceiveInstallmentRepository.save(billToReceiveInstallmentMapper.toEntity(billToReceiveInstallment)));
	}
}
