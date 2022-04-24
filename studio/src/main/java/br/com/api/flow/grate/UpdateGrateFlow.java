package br.com.api.flow.grate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.GrateDTO;
import br.com.api.dto.GrateItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.grate.item.UpdateGrateFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateGrateFlow {

	@Autowired
	private UpdateGrateFlowItem updateGrateFlowItem;

	@Transactional	
	public ResponseAPI<GrateDTO> execute(GrateDTO grateDTO, HttpHeaders headers) {

		ResponseAPI<GrateDTO> response = ResponseAPI.<GrateDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateGrateFlowItem.update(grateDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
