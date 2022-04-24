package br.com.api.flow.paymentcondition;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.paymentcondition.item.UpdatePaymentConditionFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdatePaymentConditionFlow {

	@Autowired
	private UpdatePaymentConditionFlowItem updatePaymentConditionFlowItem;

	public ResponseAPI<PaymentConditionDTO> execute(PaymentConditionDTO paymentConditionDTO, HttpHeaders headers) {

		ResponseAPI<PaymentConditionDTO> response = ResponseAPI.<PaymentConditionDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updatePaymentConditionFlowItem.update(paymentConditionDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
