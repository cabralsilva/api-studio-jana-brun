package br.com.api.flow.billtopayinstallment.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayInstallmentMapper;
import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.entity.repository.BillToPayInstallmentRepository;

@Component
public class InsertBillToPayInstallmentFlowItem {

	@Autowired
	private BillToPayInstallmentRepository billToPayInstallmentRepository;

	@Autowired
	private UpdateBillToPayInstallmentFlowItem updateBillToPayInstallmentFlowItem;

	@Autowired
	private BillToPayInstallmentMapper billToPayInstallmentMapper;

	public BillToPayInstallmentDTO insert(@NonNull BillToPayInstallmentDTO billToPayInstallment) {

		if (Objects.nonNull(billToPayInstallment.getIdentifier())) {
			return updateBillToPayInstallmentFlowItem.update(billToPayInstallment);
		}

		return billToPayInstallmentMapper.toDTO(billToPayInstallmentRepository.save(billToPayInstallmentMapper.toEntity(billToPayInstallment)));
	}
}
