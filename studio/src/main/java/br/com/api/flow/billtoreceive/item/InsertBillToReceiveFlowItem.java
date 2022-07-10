package br.com.api.flow.billtoreceive.item;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import br.com.api.converter.BillToReceiveMapper;
import br.com.api.dto.BillToReceiveDTO;
import br.com.api.entity.repository.BillToReceiveRepository;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.billtoreceiveinstallment.item.CreateBillToReceiveInstallmentFlowItem;

@Component
public class InsertBillToReceiveFlowItem {

	@Autowired
	private BillToReceiveRepository billToReceiveRepository;

	@Autowired
	private UpdateBillToReceiveFlowItem updateBillToReceiveFlowItem;

	@Autowired
	private BillToReceiveMapper billToReceiveMapper;

	@Autowired
	private CreateBillToReceiveInstallmentFlowItem createInstallmentFlowItem;

	public BillToReceiveDTO insert(@NonNull BillToReceiveDTO billToReceive)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {

		if (Objects.nonNull(billToReceive.getIdentifier())) {
			return updateBillToReceiveFlowItem.update(billToReceive);
		}

		BillToReceiveDTO billToReceiveAux = billToReceiveMapper
				.toDTO(billToReceiveRepository.save(billToReceiveMapper.toEntity(billToReceive)));
		if (ObjectUtils.isNotEmpty(billToReceive.getInstallmentList())) {
			billToReceiveAux.setInstallmentList(billToReceive.getInstallmentList());
		}
		createInstallmentFlowItem.create(billToReceiveAux);
		return billToReceive;
	}
}
