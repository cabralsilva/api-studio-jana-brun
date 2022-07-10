package br.com.api.flow.billtoreceiveinstallmentpayment;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.PaymentStatusEnum;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceiveinstallment.item.UpdateStatusBillToReceiveInstallmentFlowItem;
import br.com.api.flow.billtoreceiveinstallmentpayment.item.InsertBillToReceiveInstallmentPaymentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertBillToReceiveInstallmentPaymentFlow {

	@Autowired
	private InsertBillToReceiveInstallmentPaymentFlowItem insertBillToReceiveInstallmentPaymentFlowItem;

	@Autowired
	private UpdateStatusBillToReceiveInstallmentFlowItem updateStatusBillToReceiveInstallmentFlowItem;

	@Transactional
	public ResponseEntity<ResponseAPI<BillToReceiveInstallmentPaymentDTO>> execute(
			BillToReceiveInstallmentPaymentDTO billToReceiveInstallmentPaymentDTO, HttpHeaders headers) {

		ResponseAPI<BillToReceiveInstallmentPaymentDTO> response = ResponseAPI.<BillToReceiveInstallmentPaymentDTO>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			if (Objects.isNull(billToReceiveInstallmentPaymentDTO.getCode())) {
				billToReceiveInstallmentPaymentDTO.setCode("");
			}
			billToReceiveInstallmentPaymentDTO.setStatus(PaymentStatusEnum.SETTLED);
			response.setData(insertBillToReceiveInstallmentPaymentFlowItem.insert(billToReceiveInstallmentPaymentDTO));
			updateStatusBillToReceiveInstallmentFlowItem.update(billToReceiveInstallmentPaymentDTO.getInstallment());
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
