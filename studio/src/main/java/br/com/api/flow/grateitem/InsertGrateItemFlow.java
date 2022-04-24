package br.com.api.flow.grateitem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.GrateItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.grateitem.item.InsertGrateItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertGrateItemFlow {

	@Autowired
	private InsertGrateItemFlowItem insertGrateItemFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<GrateItemDTO> execute(GrateItemDTO countryDTO, HttpHeaders headers) {

		ResponseAPI<GrateItemDTO> response = ResponseAPI.<GrateItemDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(insertGrateItemFlowItem.insert(countryDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
