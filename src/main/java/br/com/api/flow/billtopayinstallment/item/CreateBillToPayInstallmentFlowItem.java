package br.com.api.flow.billtopayinstallment.item;

import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToPayDTO;
import br.com.api.dto.BillToPayInstallmentDTO;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.utils.Utils;

@Component
public class CreateBillToPayInstallmentFlowItem {
	
	@Autowired
	private InsertBillToPayInstallmentFlowItem insertBillToPayInstallmentFlowItem;

	public void create(BillToPayDTO billToPay) {

		Integer quantityInstallments = billToPay.getPaymentCondition().getQuantityInstallments();
		Double remainingValue = Utils.getRounded(billToPay.getValue(), 2, RoundingMode.HALF_EVEN);
		for (Integer installmentNumber = 1; installmentNumber <= quantityInstallments; installmentNumber++) {
			BillToPayInstallmentDTO installment = buildInstallment(billToPay, remainingValue, installmentNumber);
			remainingValue = remainingValue - installment.getValue();
			insertBillToPayInstallmentFlowItem.insert(installment);	
		}
	}

	private BillToPayInstallmentDTO buildInstallment(BillToPayDTO billToPay, Double remainingValue,
			Integer installmentNumber) {

		Integer quantityInstallments = billToPay.getPaymentCondition().getQuantityInstallments();

		BillToPayInstallmentDTO installment = new BillToPayInstallmentDTO();
		installment.setNumber(installmentNumber);
		installment.setBillToPay(BillToPayDTO.builder().identifier(billToPay.getIdentifier()).build());
		installment.setStatus(InstallmentStatusEnum.OPENED);
		installment.setDescription(billToPay.getTitle() + " - " + installmentNumber + "/" + quantityInstallments);

		Double installmentValue = billToPay.getValue() / quantityInstallments;
		if (installmentNumber.equals(quantityInstallments)) {
			installmentValue = remainingValue;
		}
		installmentValue = Utils.getRounded(installmentValue, 2, RoundingMode.HALF_EVEN);
		installment.setValue(installmentValue);
		
		installment.setTargetDate(billToPay.getEmissionDate().plusMonths(installmentNumber - 1));

		return installment;
	}
}
