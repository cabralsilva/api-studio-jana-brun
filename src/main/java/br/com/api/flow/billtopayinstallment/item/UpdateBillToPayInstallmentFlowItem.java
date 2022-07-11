package br.com.api.flow.billtopayinstallment.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentMapper;
import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.entity.repository.BillToPayInstallmentRepository;

@Component
public class UpdateBillToPayInstallmentFlowItem {

	@Autowired
	private BillToPayInstallmentRepository billToPayInstallmentRepository;

	@Autowired
	private BillToPayInstallmentMapper billToPayInstallmentMapper;

	public BillToPayInstallmentDTO update(BillToPayInstallmentDTO billToPayInstallment) {

		return billToPayInstallmentMapper.toDTO(billToPayInstallmentRepository.save(billToPayInstallmentMapper.toEntity(billToPayInstallment)));
	}
}
