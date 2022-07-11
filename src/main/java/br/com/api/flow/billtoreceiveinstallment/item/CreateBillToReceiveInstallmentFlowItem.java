package br.com.api.flow.billtoreceiveinstallment.item;

import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.api.dto.BillToReceiveDTO;
import br.com.api.dto.BillToReceiveInstallmentDTO;
import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.enums.InstallmentStatusEnum;
import br.com.api.enums.PaymentStatusEnum;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.billtoreceiveinstallmentpayment.item.InsertBillToReceiveInstallmentPaymentFlowItem;
import br.com.api.utils.Utils;

@Component
public class CreateBillToReceiveInstallmentFlowItem {

	@Autowired
	private InsertBillToReceiveInstallmentFlowItem insertBillToReceiveInstallmentFlowItem;

	@Autowired
	private InsertBillToReceiveInstallmentPaymentFlowItem billToReceiveInstallmentPaymentFlowItem;

	@Autowired
	private UpdateStatusBillToReceiveInstallmentFlowItem updateStatusBillToReceiveInstallmentFlowItem;

	public void create(BillToReceiveDTO billToReceive)
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException, FindByFilterException {
		if (ObjectUtils.isNotEmpty(billToReceive.getInstallmentList())) {
			for (BillToReceiveInstallmentDTO installment : billToReceive.getInstallmentList()) {
				installment
						.setBillToReceive(BillToReceiveDTO.builder().identifier(billToReceive.getIdentifier()).build());
				installment.setValue(Utils.getRounded(installment.getOriginalValue() + installment.getAdditionValue(),
						2, RoundingMode.HALF_EVEN));
				insertBillToReceiveInstallmentFlowItem.insert(installment);
			}
			return;
		}
		Integer quantityInstallments = billToReceive.getPaymentCondition().getQuantityInstallments();
		Double remainingValue = Utils.getRounded(billToReceive.getValue(), 2, RoundingMode.HALF_EVEN);
		for (Integer installmentNumber = 1; installmentNumber <= quantityInstallments; installmentNumber++) {
			BillToReceiveInstallmentDTO installment = buildInstallment(billToReceive, remainingValue,
					installmentNumber);
			remainingValue = remainingValue - installment.getValue();
			installment = insertBillToReceiveInstallmentFlowItem.insert(installment);

			if (Objects.nonNull(billToReceive.getPaymentCondition().getPaymentMethodDefault())) {
				BillToReceiveInstallmentPaymentDTO payment = new BillToReceiveInstallmentPaymentDTO();
				payment.setCode("");
				payment.setInstallment(installment);
				payment.setMethod(billToReceive.getPaymentCondition().getPaymentMethodDefault());
				payment.setPaymentDate(LocalDate.now());
				payment.setStatus(PaymentStatusEnum.SETTLED);
				payment.setValue(installment.getValue());
				billToReceiveInstallmentPaymentFlowItem.insert(payment);
				updateStatusBillToReceiveInstallmentFlowItem.update(installment);
			}
		}
	}

	private BillToReceiveInstallmentDTO buildInstallment(BillToReceiveDTO billToReceive, Double remainingValue,
			Integer installmentNumber) {

		Integer quantityInstallments = billToReceive.getPaymentCondition().getQuantityInstallments();

		BillToReceiveInstallmentDTO installment = new BillToReceiveInstallmentDTO();
		installment.setNumber(installmentNumber);
		installment.setBillToReceive(BillToReceiveDTO.builder().identifier(billToReceive.getIdentifier()).build());
		installment.setStatus(InstallmentStatusEnum.OPENED);
		installment.setDescription(billToReceive.getTitle() + " - " + installmentNumber + "/" + quantityInstallments);

		Double installmentValue = billToReceive.getValue() / quantityInstallments;
		if (installmentNumber.equals(quantityInstallments)) {
			installmentValue = remainingValue;
		}
		installmentValue = Utils.getRounded(installmentValue, 2, RoundingMode.HALF_EVEN);
		installment.setValue(installmentValue);

		installment.setTargetDate(billToReceive.getEmissionDate().plusMonths(installmentNumber - 1L));
		installment.setOriginalValue(installmentValue);
		installment.setAdditionValue(0.0D);

		return installment;
	}
}
