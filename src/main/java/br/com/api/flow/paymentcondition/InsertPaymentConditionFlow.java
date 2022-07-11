package br.com.api.flow.paymentcondition;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentConditionDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.paymentcondition.item.InsertPaymentConditionFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertPaymentConditionFlow {

	@Autowired
	private InsertPaymentConditionFlowItem insertPaymentConditionFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<PaymentConditionDTO> execute(PaymentConditionDTO paymentConditionDTO, HttpHeaders headers) {

		ResponseAPI<PaymentConditionDTO> response = ResponseAPI.<PaymentConditionDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(insertPaymentConditionFlowItem.insert(paymentConditionDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
