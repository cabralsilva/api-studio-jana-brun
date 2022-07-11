package br.com.api.flow.paymentmethod;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.PaymentMethodDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.paymentmethod.item.InsertPaymentMethodFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertPaymentMethodFlow {

	@Autowired
	private InsertPaymentMethodFlowItem insertPaymentMethodFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<PaymentMethodDTO> execute(PaymentMethodDTO paymentMethodDTO, HttpHeaders headers) {

		ResponseAPI<PaymentMethodDTO> response = ResponseAPI.<PaymentMethodDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(insertPaymentMethodFlowItem.insert(paymentMethodDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
