package br.com.api.flow.billtoreceive.item;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.dto.PaymentConditionDTO;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.flow.billtoreceiveinstallment.item.InsertBillToReceiveInstallmentFlowItem;
import lombok.var;

@Component
public class BuildInstallmentsToReceiveFlowItem {

	@Autowired
	private InsertBillToReceiveInstallmentFlowItem insertBillToReceiveInstallmentFlowItem;

	public void build(BillToReceiveDTO billToReceiveDTO, PaymentConditionDTO paymentConditionDTO) {

		var remainingBalance = billToReceiveDTO.getValue();
		for (var i = 1; i <= paymentConditionDTO.getQuantityInstallments(); i++) {

			var installment = BillToReceiveInstallmentDTO.builder()
					.description(StringUtils.join(Arrays.asList(i, paymentConditionDTO.getQuantityInstallments()), "/"))
					.number(i).status(InstallmentStatusEnum.OPENED)
					.value(paymentConditionDTO.getQuantityInstallments().equals(i) ? remainingBalance
							: billToReceiveDTO.getValue() / paymentConditionDTO.getQuantityInstallments())
					.targetDate(LocalDateTime.now().plusMonths(i)).billToReceive(billToReceiveDTO).build();
			remainingBalance -= ((BillToReceiveInstallmentDTO) installment).getValue();

			insertBillToReceiveInstallmentFlowItem.insert((BillToReceiveInstallmentDTO) installment);

		}
	}
}
