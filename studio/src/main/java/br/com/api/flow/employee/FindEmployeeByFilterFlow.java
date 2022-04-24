package br.com.api.flow.employee;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.EmployeeFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.employee.item.FindEmployeeByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class FindEmployeeByFilterFlow {

	@Autowired
	private FindEmployeeByFilterFlowItem findEmployeeByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<EmployeeFilter> execute(EmployeeFilter filter, HttpHeaders headers) {

		ResponseAPI<EmployeeFilter> response = ResponseAPI.<EmployeeFilter>builder()
				.friendlyMessagesList(new ArrayList<>()).build();
		try {
			response.setData(findEmployeeByFilterFlowItem.findByFilter(filter));
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
			e.printStackTrace();
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
