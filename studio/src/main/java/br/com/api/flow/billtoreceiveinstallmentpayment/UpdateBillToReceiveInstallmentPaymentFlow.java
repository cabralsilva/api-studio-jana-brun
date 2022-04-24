package br.com.api.flow.billtoreceiveinstallmentpayment;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.BillToReceiveInstallmentPaymentDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.billtoreceiveinstallmentpayment.item.UpdateBillToReceiveInstallmentPaymentFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateBillToReceiveInstallmentPaymentFlow {

	@Autowired
	private UpdateBillToReceiveInstallmentPaymentFlowItem updateBillToReceiveInstallmentPaymentFlowItem;

	public ResponseAPI execute(BillToReceiveInstallmentPaymentDTO billToReceiveInstallmentPaymentDTO, HttpHeaders headers) {

		ResponseAPI response = ResponseAPI.builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateBillToReceiveInstallmentPaymentFlowItem.update(billToReceiveInstallmentPaymentDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}