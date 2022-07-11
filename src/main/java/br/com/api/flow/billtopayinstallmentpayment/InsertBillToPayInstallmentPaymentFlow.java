package br.com.api.flow.billtopayinstallmentpayment;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToPayInstallmentPaymentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.PaymentStatusEnum;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtopayinstallment.item.UpdateStatusBillToPayInstallmentFlowItem;
import br.com.api.flow.billtopayinstallmentpayment.item.InsertBillToPayInstallmentPaymentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertBillToPayInstallmentPaymentFlow {

	@Autowired
	private InsertBillToPayInstallmentPaymentFlowItem insertBillToPayInstallmentPaymentFlowItem;

	@Autowired
	private UpdateStatusBillToPayInstallmentFlowItem updateStatusBillToPayInstallmentFlowItem;

	@Transactional
	public ResponseEntity<ResponseAPI<BillToPayInstallmentPaymentDTO>> execute(
			BillToPayInstallmentPaymentDTO billToPayInstallmentPaymentDTO, HttpHeaders headers) {

		ResponseAPI<BillToPayInstallmentPaymentDTO> response = ResponseAPI.<BillToPayInstallmentPaymentDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			if (Objects.isNull(billToPayInstallmentPaymentDTO.getCode())) {
				billToPayInstallmentPaymentDTO.setCode("");
			}
			billToPayInstallmentPaymentDTO.setStatus(PaymentStatusEnum.SETTLED);
			response.setData(insertBillToPayInstallmentPaymentFlowItem.insert(billToPayInstallmentPaymentDTO));
			updateStatusBillToPayInstallmentFlowItem.update(billToPayInstallmentPaymentDTO.getInstallment());
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
		}

		return ResponseEntity.ok(response);
	}
}
