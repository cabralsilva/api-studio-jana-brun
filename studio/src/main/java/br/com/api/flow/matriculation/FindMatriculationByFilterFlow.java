package br.com.api.flow.matriculation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.MatriculationFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.matriculation.item.FindMatriculationByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class FindMatriculationByFilterFlow {

	@Autowired
	private FindMatriculationByFilterFlowItem findMatriculationByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<MatriculationFilter> execute(MatriculationFilter filter, HttpHeaders headers) {

		ResponseAPI<MatriculationFilter> response = ResponseAPI.<MatriculationFilter>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findMatriculationByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
