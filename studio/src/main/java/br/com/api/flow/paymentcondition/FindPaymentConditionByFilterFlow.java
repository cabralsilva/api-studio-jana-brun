package br.com.api.flow.paymentcondition;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.PaymentConditionFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.paymentcondition.item.FindPaymentConditionByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class FindPaymentConditionByFilterFlow {

	@Autowired
	private FindPaymentConditionByFilterFlowItem findPaymentConditionByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<PaymentConditionFilter> execute(PaymentConditionFilter filter, HttpHeaders headers) {

		ResponseAPI<PaymentConditionFilter> response = ResponseAPI.<PaymentConditionFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findPaymentConditionByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			response.getFriendlyMessagesList().add(messageSource.getMessage(e.getMessage(), null,
					Utils.getLocale(headers.getAcceptLanguageAsLocales())));
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
