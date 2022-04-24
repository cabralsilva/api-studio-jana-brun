package br.com.api.flow.grate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import br.com.api.dto.GrateDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.grate.item.InsertGrateFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class InsertGrateFlow {

	@Autowired
	private InsertGrateFlowItem insertGrateFlowItem;

	@Autowired
	private MessageSource messageSource;

	public ResponseAPI<GrateDTO> execute(GrateDTO countryDTO, HttpHeaders headers) {

		ResponseAPI<GrateDTO> response = ResponseAPI.<GrateDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(insertGrateFlowItem.insert(countryDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
