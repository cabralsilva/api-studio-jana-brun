package br.com.api.flow.grateitem;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.api.dto.GrateItemDTO;
import br.com.api.enums.LevelReport;
import br.com.api.enums.StatusResponse;
import br.com.api.flow.grateitem.item.UpdateGrateItemFlowItem;
import br.com.api.utils.ReportTech;
import br.com.api.utils.ResponseAPI;

@Service
public class UpdateGrateItemFlow {

	@Autowired
	private UpdateGrateItemFlowItem updateGrateItemFlowItem;

	@Transactional	
	public ResponseAPI<GrateItemDTO> execute(GrateItemDTO grateDTO, HttpHeaders headers) {

		ResponseAPI<GrateItemDTO> response = ResponseAPI.<GrateItemDTO>builder().friendlyMessagesList(new ArrayList<>()).build();

		try {
			response.setData(updateGrateItemFlowItem.update(grateDTO));
			response.setStatus(StatusResponse.SUCCESS);
		} catch (Exception e) {
			response.setStatus(StatusResponse.ERROR);

			response.setReportTech(ReportTech.builder().level(LevelReport.ERROR).code(e.getMessage())
					.message(e.getLocalizedMessage()).exception(e).build());
		}

		return response;
	}
}
