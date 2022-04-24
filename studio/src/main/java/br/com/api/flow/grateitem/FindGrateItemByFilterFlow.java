package br.com.api.flow.grateitem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.entity.repository.GrateItemFilter;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.exceptions.FindByFilterException;
import br.com.api.flow.grateitem.item.FindGrateItemByFilterFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;
import br.com.api.utils.Utils;

@Service
public class FindGrateItemByFilterFlow {

	@Autowired
	private FindGrateItemByFilterFlowItem findGrateItemByFilterFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<GrateItemFilter> execute(GrateItemFilter filter, HttpHeaders headers) {

		ResponseAPI<GrateItemFilter> response = ResponseAPI.<GrateItemFilter>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(findGrateItemByFilterFlowItem.findByFilter(filter));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
