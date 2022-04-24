package br.com.api.flow.city;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.CityFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.city.item.FindCityByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class FindCityByFilterFlow {

	@Autowired
	private FindCityByFilterFlowItem findCityByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<CityFilter> execute(CityFilter filter, HttpHeaders headers) {

		ResponseAPI<CityFilter> response = ResponseAPI.<CityFilter>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findCityByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (FindByFilterException e) {
			response.setStatus(StatusResponse.ERROR);
			response.getFriendlyMessagesList().add(messageSource.getMessage(e.getMessage(), null,
					Utils.getLocale(headers.getAcceptLanguageAsLocales())));

			response.setReportTech(ReportTech
					.builder().level(LevelReport.FAILURE).code(e.getCode()).message(messageSource
							.getMessage(e.getMessage(), null, Utils.getLocale(headers.getAcceptLanguageAsLocales())))
					.exception(e).build());
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
