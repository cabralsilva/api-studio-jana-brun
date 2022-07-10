package br.com.api.flow.billtopay.item;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToPayMapper;
import br.com.api.dto.BillToPayDTO;
import br.com.api.entity.repository.BillToPayRepository;
import br.com.api.flow.billtopayinstallment.item.CreateBillToPayInstallmentFlowItem;

@Component
public class InsertBillToPayFlowItem {

	@Autowired
	private BillToPayRepository billToPayRepository;

	@Autowired
	private UpdateBillToPayFlowItem updateBillToPayFlowItem;

	@Autowired
	private BillToPayMapper billToPayMapper;

	@Autowired
	private CreateBillToPayInstallmentFlowItem createInstallmentFlowItem;

	public BillToPayDTO insert(@NonNull BillToPayDTO billToPay) {

		if (Objects.nonNull(billToPay.getIdentifier())) {
			return updateBillToPayFlowItem.update(billToPay);
		}

		BillToPayDTO billToPayDTO = billToPayMapper
				.toDTO(billToPayRepository.save(billToPayMapper.toEntity(billToPay)));
		createInstallmentFlowItem.create(billToPayDTO);
		return billToPayDTO;
	}
}
