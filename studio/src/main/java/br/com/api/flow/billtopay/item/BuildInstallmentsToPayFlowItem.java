package br.com.api.flow.billtopay.item;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToPayDTO;
import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.dto.PaymentConditionDTO;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.flow.billtopayinstallment.item.InsertBillToPayInstallmentFlowItem;
import lombok.var;

@Component
public class BuildInstallmentsToPayFlowItem {

	@Autowired
	private InsertBillToPayInstallmentFlowItem insertBillToPayInstallmentFlowItem;

	public void build(BillToPayDTO billToPayDTO, PaymentConditionDTO paymentConditionDTO) {

		var remainingBalance = billToPayDTO.getValue();
		for (var i = 1; i <= paymentConditionDTO.getQuantityInstallments(); i++) {

			var installment = BillToPayInstallmentDTO.builder()
					.description(StringUtils.join(Arrays.asList	(i, paymentConditionDTO.getQuantityInstallments()), "/"))
					.number(i).status(InstallmentStatusEnum.OPENED)
					.value(paymentConditionDTO.getQuantityInstallments().equals(i) ? remainingBalance
							: billToPayDTO.getValue() / paymentConditionDTO.getQuantityInstallments())
					.targetDate(LocalDateTime.now().plusMonths(i)).billToPay(billToPayDTO).build();
			remainingBalance -= ((BillToPayInstallmentDTO) installment).getValue();

			insertBillToPayInstallmentFlowItem.insert((BillToPayInstallmentDTO) installment);

		}
	}
}
