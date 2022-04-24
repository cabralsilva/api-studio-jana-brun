package br.com.api.flow.state;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.StateFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.state.item.FindStateByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class FindStateByFilterFlow {

	@Autowired
	private FindStateByFilterFlowItem findStateByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<StateFilter> execute(StateFilter filter, HttpHeaders headers) {

		ResponseAPI<StateFilter> response = ResponseAPI.<StateFilter>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findStateByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (FindByFilterException e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);
			response.getFriendlyMessagesList().add(messageSource.getMessage(e.getMessage(), null,
					Utils.getLocale(headers.getAcceptLanguageAsLocales())));

			response.setReportTech(ReportTech
					.builder().level(LevelReport.FAILURE).code(e.getCode()).message(messageSource
							.getMessage(e.getMessage(), null, Utils.getLocale(headers.getAcceptLanguageAsLocales())))
					.exception(e).build());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
